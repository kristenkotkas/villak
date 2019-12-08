import {Injectable} from '@angular/core';
import {Response} from 'selenium-webdriver/http';
import * as SockJs from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Action} from '../game-data/model/action';
import {Game} from '../game-data/model/game';
import {Message} from '../game-data/model/message';
import {Util} from './util';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private URL: string = Util.getApiUrl() + '/socket';

  private socket = new SockJs(this.URL);
  private stompClient = Stomp.over(this.socket);

  constructor() {
  }

  listen(callback: (nextGame: Game) => void): void {
    this.stompClient.connect({}, frame => {
      this.stompClient.subscribe('/response', response => {
        callback(Game.parseGame(response.body));
      });
      this.send([{action: Action.GET_CURRENT, id: -1}]);
    });
  }

  send(messages: Message[]): Promise<Response> {
    return this.stompClient.send('/request', {}, JSON.stringify(messages));
  }

}

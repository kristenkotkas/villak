import {Injectable} from '@angular/core';
import * as SockJs from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Util} from '../../common/util';
import {Game} from "../model/game";
import {Message} from "../model/message";
import {Action} from "../model/action";

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
      this.stompClient.subscribe('/roosid_response', response => {
        callback(Game.parseGame(response.body));
      });
      this.send([{action: Action.GET_CURRENT, id: -1}]);
    });
  }

  send(messages: Message[]): Promise<Response> {
    return this.stompClient.send('/roosid_request', {}, JSON.stringify(messages));
  }

}

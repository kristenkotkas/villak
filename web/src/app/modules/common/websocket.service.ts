import {Injectable} from '@angular/core';
import {Response} from 'selenium-webdriver/http';
import * as SockJs from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Message} from '../model/message';
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

  listen(callback: (msg: Message) => void): void {
    this.stompClient.connect({}, frame => {
      this.stompClient.subscribe('/response', response => {
        callback(new Message(response));
      });
    });
  }

  send(message: Message): Promise<Response> {
    return this.stompClient.send('/request', {}, JSON.stringify(message));
  }

}

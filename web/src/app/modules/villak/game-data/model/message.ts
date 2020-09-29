import {Action} from './action';

export class Message {
  action: Action = undefined;
  id: number = undefined;
  payload?: number = undefined;

  constructor(input: any) {
    const json = JSON.parse(input.body);
    this.action = json['action'];
    this.id = json['id'];
    this.payload = json['payload'];
  }
}

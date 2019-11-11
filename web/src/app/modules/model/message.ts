import {Action} from '../game-data/model/action';

export class Message {
  event: Action = undefined;
  id: number = undefined;
  payload?: number = undefined;

  constructor(input: any) {
    const json = JSON.parse(input.body);
    this.event = json['event'];
    this.id = json['id'];
    this.payload = json['payload'];
  }
}

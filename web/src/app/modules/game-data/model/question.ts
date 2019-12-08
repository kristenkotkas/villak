import {Action} from './action';

export class Question {
  id: number;
  amount: number;
  silver: boolean;
  question: string;
  answer: string;
  state: Action;
  pictureUri: string = '';
  soundUri: string = '';

  constructor(id: number,
              amount: number,
              isSilver: boolean,
              question: string,
              answer: string,
              pictureUri?: string,
              sound?: string) {
    this.id = id;
    this.amount = amount;
    this.silver = isSilver;
    this.question = question;
    this.answer = answer;
    this.state = Action.CLOSE;
    this.pictureUri = pictureUri;
    this.soundUri = sound;
  }
}

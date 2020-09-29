import {Answer} from "./answer";

export class Round {
  id: number = undefined;
  question: string = undefined;
  answers: Answer[] = undefined;
  multiplayer: number = undefined;
  score: number = undefined;
  slots: number = undefined;
  active: boolean = undefined;
}

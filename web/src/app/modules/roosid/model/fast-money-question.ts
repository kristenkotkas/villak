import {FastMoneyAnswer} from "./fast-money-answer";
import {FastMoneyPlayerResponse} from "./fast-money-player-response";

export class FastMoneyQuestion {
  id: number = undefined;
  question: string = undefined;
  answers: FastMoneyAnswer[] = undefined;
  firstPlayer: FastMoneyPlayerResponse = undefined;
  secondPlayer: FastMoneyPlayerResponse = undefined;
}

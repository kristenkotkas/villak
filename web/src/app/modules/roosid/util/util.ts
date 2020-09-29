import {Game} from "../model/game";
import {Round} from "../model/round";

export class Util {

  static getActiveRound(game: Game): Round {
    return game.rounds.filter((round: Round) => round.active)[0];
  }

}

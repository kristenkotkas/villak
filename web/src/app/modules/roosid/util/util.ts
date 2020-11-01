import {Game} from "../model/game";
import {Round} from "../model/round";

export class Util {

  static getActiveRound(game: Game): Round {
    return game.rounds.filter((round: Round) => round.active)[0];
  }

  static isPresent(value: any): boolean {
    return value !== undefined && value !== null;
  }

  static getPlayer(player: string): HTMLElement {
    return <HTMLElement>document.getElementById('player_' + player);
  }

}

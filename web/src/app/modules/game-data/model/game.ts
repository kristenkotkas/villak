import {Round} from './round';
import {Team} from './team';
import {Settings} from "./settings";

export class Game {
  teams: Team[] = undefined;
  rounds: Round[] = undefined;
  settings: Settings = undefined;

  public static parseGame(input: any): Game {
    const json = JSON.parse(input);
    return {
      teams: json['teams'],
      rounds: json['rounds'],
      settings: json['settings']
    };
  }

  constructor(teams: Team[], rounds: Round[]) {
    this.teams = teams;
    this.rounds = rounds;
  }
}

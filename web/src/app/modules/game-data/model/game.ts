import {Round} from './round';
import {Team} from './team';

export class Game {
  teams: Team[] = undefined;
  rounds: Round[] = undefined;

  public static parseGame(input: any): Game {
    const json = JSON.parse(input);
    return {
      teams: json['teams'],
      rounds: json['rounds']
    };
  }

  constructor(teams: Team[], rounds: Round[]) {
    this.teams = teams;
    this.rounds = rounds;
  }
}

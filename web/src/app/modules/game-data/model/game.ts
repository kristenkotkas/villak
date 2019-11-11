import {Round} from './round';
import {Team} from './team';

export class Game {
  teams: Team[];
  rounds: Round[];

  constructor(teams: Team[], rounds: Round[]) {
    this.teams = teams;
    this.rounds = rounds;
  }
}

import {Message} from "./message";
import {Team} from "./team";
import {Round} from "./round";

export class Game {
  teams: Team[] = undefined;
  rounds: Round[] = undefined;
  latestMessages: Message[] = undefined;

  public static parseGame(input: any): Game {
    const json = JSON.parse(input);
    return {
      teams: json['teams'],
      rounds: json['rounds'],
      latestMessages: json['latestMessages']
    };
  }

  constructor(teams: Team[], rounds: Round[], latestMessages: Message[]) {
    this.teams = teams;
    this.rounds = rounds;
    this.latestMessages = latestMessages;
  }
}

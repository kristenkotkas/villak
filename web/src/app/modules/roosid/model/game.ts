import {Message} from "./message";
import {Round} from "./round";
import {Settings} from "./settings";
import {Team} from "./team";

export class Game {
  teams: Team[] = undefined;
  rounds: Round[] = undefined;
  latestMessages: Message[] = undefined;
  settings: Settings = undefined;

  public static parseGame(input: any): Game {
    const json = JSON.parse(input);
    return {
      teams: json['teams'],
      rounds: json['rounds'],
      latestMessages: json['latestMessages'],
      settings: json['settings']
    };
  }

  constructor(teams: Team[], rounds: Round[], latestMessages: Message[], settings: Settings) {
    this.teams = teams;
    this.rounds = rounds;
    this.latestMessages = latestMessages;
    this.settings = settings;
  }
}

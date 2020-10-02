import {Component, OnInit} from '@angular/core';
import {GameRepository} from "../data/game.repository";
import {WebsocketService} from "../data/websocket.service";
import {Action} from "../model/action";
import {Answer} from "../model/answer";
import {AnswerState} from "../model/answer-state";
import {Game} from "../model/game";
import {Round} from "../model/round";
import {Team} from "../model/team";

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {

  game: Game;
  private idGenerator: number = 0;

  constructor(private gameRepo: GameRepository,
              private ws: WebsocketService) {
  }

  ngOnInit(): void {
    this.getGame();
  }

  private evalIdGenerator(game: Game): void {
    game.teams.forEach((t: Team) => this.idGenerator = t.id > this.idGenerator ? t.id : this.idGenerator);
    game.rounds.forEach((r: Round) => {
      if (r.id > this.idGenerator) {
        this.idGenerator = r.id;
      }
      r.answers.forEach((a: Answer) => {
        if (a.id > this.idGenerator) {
          this.idGenerator = a.id;
        }
      });
    });
    console.log(this.idGenerator);

  }

  saveGame(): void {
    this.gameRepo.createGame(this.game).then(() => {
      this.getGame();
      this.ws.send([{
        action: Action.GET_CURRENT,
        id: -1
      }]);
    });
  }

  private getGame(): void {
    this.gameRepo.getGame().then((game: Game) => {
      console.log(game);
      this.game = game || new Game([], [], []);
      this.evalIdGenerator(game);
    });
  }

  addEmptyTeam(): void {
    if (!this.isTeamsFull()) {
      let team = new Team();
      team.id = ++this.idGenerator;
      team.crossCount = 0;
      team.score = 0;
      this.game.teams.push(team);
    }
  }

  isTeamsFull(): boolean {
    return this.game.teams.length >= 2;
  }

  deleteTeam(teamId: number): void {
    this.game.teams = this.game.teams.filter((team: Team) => team.id !== teamId);
  }

  addEmptyRound(): void {
    let round = new Round();
    round.id = ++this.idGenerator;
    round.active = false;
    round.score = 0;
    round.answers = [];
    this.game.rounds.push(round);
  }

  deleteRound(roundId: number): void {
    this.game.rounds = this.game.rounds.filter((round: Round) => round.id !== roundId);
  }

  addEmptyAnswer(roundId: number): void {
    this.game.rounds
      .filter((round: Round) => {
        if (round.id === roundId) {
          let answer = new Answer();
          answer.id = ++this.idGenerator;
          answer.state = AnswerState.CLOSED;
          round.answers.push(answer);
        }
      });
  }

  isAnswersFull(roundId: number): boolean {
    return this.game.rounds.filter((round: Round) => round.id === roundId)[0].answers.length >= 6;
  }

  deleteAnswer(roundId: number, answerId: number): void {
    this.game.rounds.forEach((round: Round) => {
      if (round.id === roundId) {
        round.answers = round.answers.filter((answer: Answer) => answer.id !== answerId);
      }
    });
  }

}

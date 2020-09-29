import {Component, OnInit} from '@angular/core';
import {DataService} from "../data/data.service";
import {WebsocketService} from "../data/websocket.service";
import {Action} from "../model/action";
import {Answer} from "../model/answer";
import {AnswerState} from "../model/answer-state";
import {Game} from "../model/game";
import {Round} from "../model/round";
import {Util} from "../util/util";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  game: Game;
  activeRound: Round;

  constructor(private dataService: DataService,
              private ws: WebsocketService) {
  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe((game: Game) => {
      this.game = game;
      this.activeRound = this.getActiveRound(this.game);
      console.log(this.game);
    });
  }

  getRounds(game: Game): Round[] {
    return game.rounds.map((round: Round) => round);
  }

  getActiveRound(game: Game): Round {
    return Util.getActiveRound(game);
  }

  setActive(roundId: number): void {
    this.ws.send([{
      action: Action.SET_ACTIVE_ROUND,
      id: roundId
    }]);
  }

  setInActive(roundId: number): void {
    this.ws.send([{
      action: Action.SET_INACTIVE_ROUND,
      id: roundId
    }]);
  }

  toggleAnswer(answerId: number): void {
    this.ws.send([{
      action: Action.TOGGLE_ANSWER,
      id: answerId
    }]);
  }

  isAnswerClosed(answer: Answer): boolean {
    return answer.state === AnswerState.CLOSED;
  }

  isAnswerOpen(answer: Answer): boolean {
    return answer.state === AnswerState.OPENED;
  }

  addCurrentToScore(teamId: number): void {
    this.ws.send([{
      action: Action.ADD_CURRENT_SCORE_TO_TEAM,
      id: teamId
    }]);
  }

  resetScore(teamId: number): void {
    this.ws.send([{
      action: Action.RESET_SCORE,
      id: teamId
    }]);
  }

  getCrosses(crossCount: number): string {
    return 'X '.repeat(crossCount);
  }

  addCross(teamId: number): void {
    this.ws.send([{
      action: Action.ADD_CROSS,
      id: teamId
    }]);
  }

  resetCross(teamId: number): void {
    this.ws.send([{
      action: Action.RESET_CROSS,
      id: teamId
    }]);
  }

}

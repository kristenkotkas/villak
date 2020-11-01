import {Component, OnInit} from '@angular/core';
import {DataService} from "../data/data.service";
import {WebsocketService} from "../data/websocket.service";
import {Action} from "../model/action";
import {Answer} from "../model/answer";
import {AnswerState} from "../model/answer-state";
import {FastMoneyQuestion} from "../model/fast-money-question";
import {Game} from "../model/game";
import {Message} from "../model/message";
import {Round} from "../model/round";
import {Team} from "../model/team";
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

  setFastMoneyActive(): void {
    this.ws.send([{
      action: Action.SET_FAST_MONEY_ACTIVE,
      id: -1
    }]);
  }

  setFastMoneyInActive(): void {
    this.ws.send([{
      action: Action.SET_FAST_MONEY_INACTIVE,
      id: -1
    }]);
  }

  setInActive(roundId: number): void {
    this.ws.send([{
      action: Action.SET_INACTIVE_ROUND,
      id: roundId
    }, {
      action: Action.RESET_CROSS_ALL,
      id: -1
    }
    ]);
  }

  toggleAnswer(answerId: number): void {
    let messages: Message[] = [{
      action: Action.TOGGLE_ANSWER,
      id: answerId
    }];
    this.addThreeCrossesMessage(messages);
    this.addRoundWinnerForCorrectAnswer(messages);
    this.ws.send(messages);
  }

  isAnswerClosed(answer: Answer): boolean {
    return answer.state === AnswerState.CLOSED;
  }

  isAnswerOpen(answer: Answer): boolean {
    return answer.state === AnswerState.OPENED;
  }

  addCurrentToScore(teamId: number): void {
    this.ws.send([
      {
        action: Action.SET_ROUND_WINNER,
        id: teamId
      },
      {
        action: Action.ADD_CURRENT_SCORE_TO_TEAM,
        id: teamId
      }
    ]);
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
    let messages: Message[] = [{
      action: Action.ADD_CROSS,
      id: teamId,
      payload: 1
    }];
    this.addThreeCrossesMessage(messages);
    this.addRoundWinnerForCrosses(teamId, messages);
    this.ws.send(messages);
  }

  resetCross(teamId: number): void {
    this.ws.send([{
      action: Action.RESET_CROSS,
      id: teamId
    }]);
  }

  isThreeCrosses(game: Game): boolean {
    return game.teams.filter((team: Team) => team.crossCount === 3).length > 0;
  }

  private addThreeCrossesMessage(messages: Message[]): void {
    if (this.isThreeCrosses(this.game) && !this.activeRound.scoreToWin) {
      messages.push({
          action: Action.PLAY_SHORT_THEME,
          id: -1
        }, {
          action: Action.SET_SCORE_TO_WIN,
          id: -1
        }
      );
    }
  }

  stopShortPlayer(): void {
    this.ws.send([{
      action: Action.STOP_SHORT_THEME,
      id: -1
    }]);
  }

  playIntro(): void {
    this.ws.send([{
      action: Action.PLAY_INTRO,
      id: -1
    }]);
  }

  stopIntro(): void {
    this.ws.send([{
      action: Action.STOP_INTRO,
      id: -1
    }]);
  }

  private addRoundWinnerForCorrectAnswer(messages: Message[]): void {
    if (!this.isWinnerPresent() && this.isThreeCrosses(this.game)) {
      const winnerTeamId = this.game.teams.filter((t: Team) => t.crossCount === 0)[0].id;
      messages.push({
          action: Action.SET_ROUND_WINNER,
          id: winnerTeamId
        },
        {
          action: Action.ADD_CURRENT_SCORE_TO_TEAM,
          id: winnerTeamId
        });
    }
  }

  private addRoundWinnerForCrosses(teamIdWhoCrossed: number, messages: Message[]): void {
    if (!this.isWinnerPresent() && this.isThreeCrosses(this.game)) {
      const winnerTeamId = this.game.teams.filter((t: Team) => t.id !== teamIdWhoCrossed)[0].id;
      messages.push({
          action: Action.SET_ROUND_WINNER,
          id: winnerTeamId
        },
        {
          action: Action.ADD_CURRENT_SCORE_TO_TEAM,
          id: winnerTeamId
        },
        {
          action: Action.ADD_CROSS,
          id: teamIdWhoCrossed,
          payload: 2
        }
      );
    }
  }

  private isWinnerPresent(): boolean {
    return this.activeRound && this.activeRound.winnerTeamId !== null && this.activeRound.winnerTeamId !== undefined;
  }

  isTeamWinner(teamId: number): boolean {
    return this.activeRound && this.activeRound.winnerTeamId === teamId;
  }

  getTeamByTeamId(teamId: number): Team {
    if (teamId !== null && teamId !== undefined) {
      return this.game.teams.filter((t: Team) => t.id === teamId)[0];
    }
  }

  restartGame(): void {
    this.ws.send([{
      action: Action.RESTART_GAME,
      id: -1
    }]);
  }

  changeBuffer(value: number): void {
    this.ws.send([{
      action: Action.CHANGE_BUFFER,
      id: -1,
      payload: value
    }]);
  }

  isActiveRoundButtonVisible(roundId: number): boolean {
    return this.activeRound ? this.activeRound.id === roundId : true;
  }

  isFastMoneyActive(): boolean {
    return this.game.fastMoney.active;
  }

  changeZoom(value: number): void {
    this.ws.send([{
      action: Action.CHANGE_ZOOM,
      id: -1,
      payload: value
    }]);
  }

  setTeamAnswering(teamId: number): void {
    if (this.activeRound) {
      if (this.isTeamAnswering(this.activeRound, teamId)) {
        this.ws.send([{
          action: Action.SET_ANSWERING_FOR_ROUND,
          id: undefined,
        }]);
      } else {
        this.ws.send([{
          action: Action.SET_ANSWERING_FOR_ROUND,
          id: teamId,
        }]);
      }
    }
  }

  isTeamAnswering(round: Round, teamId: number): boolean {
    return round.answeringTeamId === teamId;
  }

  savePlayerAnswers(): void {
    let payload: FastMoneyAnswerPayload[] = [];
    this.game.fastMoney.questions.forEach((q: FastMoneyQuestion) => {
      payload.push({
        questionId: q.id,
        firstPlayerAnswer: q.firstPlayer.answer,
        firstPlayerScore: q.firstPlayer.score == -1 ? undefined : q.firstPlayer.score,
        secondPlayerAnswer: q.secondPlayer.answer,
        secondPlayerScore: q.secondPlayer.score == -1 ? undefined : q.secondPlayer.score
      });
    });
    this.ws.send([{
      action: Action.SAVE_PLAYER_ANSWERS,
      id: -1,
      payload: payload
    }]);
  }

  toggleFastMoneyAnswer(playerId: number): void {
    this.ws.send([{
      action: Action.TOGGLE_FAST_MONEY_ANSWER,
      id: playerId,
    }]);
  }

  answerOnChange($event: Event): void {
    console.log($event);
  }
}

interface FastMoneyAnswerPayload {
  questionId: number;
  firstPlayerAnswer: string;
  secondPlayerAnswer: string;
  firstPlayerScore: number;
  secondPlayerScore: number;
}

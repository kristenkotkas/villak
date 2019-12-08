import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {GameProvider} from '../game-data/game-provider';
import {Action} from '../game-data/model/action';
import {Category} from '../game-data/model/category';
import {Game} from '../game-data/model/game';
import {Question} from '../game-data/model/question';
import {Round} from '../game-data/model/round';
import {Team} from '../game-data/model/team';
import {Message} from '../game-data/model/message';
import {WebsocketService} from './websocket.service';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private data: BehaviorSubject<Game> = new BehaviorSubject(GameProvider.createGame());
  currentData = this.data.asObservable();

  constructor(private ws: WebsocketService) {
    ws.listen((game: Game) => {
      this.data.next(game);
    });
  }

  private updateDate(message: Message): void {
    if (message.action === Action.OPEN || message.action === Action.CLOSE ||
      message.action === Action.ANSWERED || message.action === Action.SILVER) {
      this.handleQuestionEvent(message);
    } else if (message.action === Action.SCORE) {
      this.handleScoreEvent(message);
    } else if (message.action === Action.CATEGORY_SHOW || message.action === Action.CATEGORY_HIDE) {
      this.handleOpenQuestion(message);
    } else if (message.action === Action.ACTIVE_ROUND) {
      this.handleActiveRound(message);
    } else if (message.action === Action.PRESS_BUTTON) {
      this.handlePressButton(message);
    } else if (message.action === Action.RESET_BUTTON) {
      this.handleResetButton(message);
    } else if (message.action === Action.SHOW_QUICKEST) {
      this.handleShowQuickest(message);
    } else if (message.action === Action.CLOSE_BUTTON) {
      this.handleCloseButton(message);
    } else if (message.action === Action.TOGGLE_WINNER) {
      this.handleToggleWinner(message);
    }
  }

  private handleQuestionEvent(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);
    next.rounds.forEach((round: Round) => {
      round.categories.forEach((cat: Category) => {
        cat.questions.forEach((que: Question) => {
          if (que.id === message.id) {
            que.state = message.action;
          }
        });
      });
    });
    this.data.next(next);
  }

  private handleScoreEvent(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);
    next.teams.forEach((team: Team) => {
      if (team.id === message.id) {
        team.score += message.payload;
      }
    });
    this.data.next(next);
  }

  private handleOpenQuestion(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);
    next.rounds.forEach((round: Round) => {
      round.categories.forEach((cat: Category) => {
        if (cat.id === message.id) {
          cat.nameState = (<Action.CATEGORY_SHOW | Action.CATEGORY_HIDE> message.action);
        }
      });
    });
    this.data.next(next);
  }

  private handleActiveRound(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);
    next.rounds.forEach((round: Round) => {
      round.active = round.id === message.id;
    });
    this.data.next(next);
  }

  private handlePressButton(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);
    next.teams.forEach((team: Team) => {
      if (team.id === message.id) {
        team.havePressed = true;
        team.timePressed = message.payload;
      }
    });
    this.data.next(next);
  }

  private handleResetButton(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);
    next.teams.forEach((team: Team) => {
      team.havePressed = false;
      team.timePressed = 0;
      team.quickest = false;
    });
    this.data.next(next);
  }

  private handleShowQuickest(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);
    let teams: Team[] = next.teams
      .filter((team: Team) => team.timePressed > 0)
      .sort(((a, b) => a.timePressed - b.timePressed));
    if (teams.length > 0) {
      teams[0].quickest = true;
    }
    this.data.next(next);
  }

  private handleCloseButton(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);
    next.teams.forEach((team: Team) => {
      team.havePressed = true;
      team.timePressed = 0;
      team.quickest = false;
    });
    this.data.next(next);
  }

  private handleToggleWinner(message: Message): void {
    let next: Game = Object.assign({}, this.data.value);

    next.teams.forEach((team: Team) => {
      if (team.id === message.id) {
        team.winner = !team.winner;
      }
    });

    this.data.next(next);
  }
}

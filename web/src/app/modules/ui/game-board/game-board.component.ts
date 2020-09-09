import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Util} from '../../common/util';
import {WebsocketService} from '../../common/websocket.service';
import {Action} from '../../game-data/model/action';
import {Game} from '../../game-data/model/game';
import {Question} from '../../game-data/model/question';
import {Round} from '../../game-data/model/round';

@Component({
  selector: 'app-game-board',
  templateUrl: './game-board.component.html',
  styleUrls: ['./game-board.component.css']
})
export class GameBoardComponent implements OnInit, OnChanges {

  @Input() game: Game;
  @Input() admin: boolean = false;
  activeRound: Round;

  constructor(private ws: WebsocketService) {
  }

  ngOnInit(): void {
    this.activeRound = this.game.rounds
      .find((round: Round) => round.active);
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.activeRound = this.game.rounds
      .find((round: Round) => round.active);
  }

  isStateClosed(question: Question): boolean {
    return question.state === Action.QUESTION_CLOSE;
  }

  isStateOpen(question: Question): boolean {
    return question.state === Action.QUESTION_OPEN;
  }

  isStateAnswered(question: Question): boolean {
    return question.state === Action.QUESTION_ANSWERED;
  }

  isStateSilver(question: Question): boolean {
    return question.state === Action.QUESTION_SILVER;
  }

  isWithPicture(question: Question): boolean {
    return question.pictureUri !== undefined && question.pictureUri !== null;
  }

  show(question: Question): void {
    this.ws.send([
      {
        action: Action.QUESTION_OPEN,
        id: question.id
      },
      {
        action: Action.RESET_BUTTON,
        id: -1
      }
    ]);
  }

  showSilver(question: Question): void {
    this.ws.send([{
      action: Action.QUESTION_SILVER,
      id: question.id
    }]);
  }

  setAnswered(question: Question): void {
    this.ws.send([
      {
        action: Action.QUESTION_ANSWERED,
        id: question.id
      },
      {
        action: Action.CLOSE_BUTTON,
        id: -1
      }
    ]);
  }

  reset(question: Question): void {
    this.ws.send([{
      action: Action.QUESTION_CLOSE,
      id: question.id
    }]);
  }

  getPlayer(player: string): HTMLElement {
    return Util.getPlayer(player);
  }

  play(player: string): void {
    // @ts-ignore
    this.getPlayer(player).play();
  }

  pause(player: string): void {
    // @ts-ignore
    this.getPlayer(player).pause();
  }

  isWithSound(question: Question): boolean {
    return !!question.soundUri;
  }
}

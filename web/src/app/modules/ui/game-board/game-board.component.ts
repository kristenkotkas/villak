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
    this.game.rounds.forEach((round: Round) => {
      if (round.active) {
        this.activeRound = round;
      }
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.game.rounds.forEach((round: Round) => {
      if (round.active) {
        this.activeRound = round;
      }
    });
  }

  isStateClosed(question: Question): boolean {
    return question.state === Action.CLOSE;
  }

  isStateOpen(question: Question): boolean {
    return question.state === Action.OPEN;
  }

  isStateAnswered(question: Question): boolean {
    return question.state === Action.ANSWERED;
  }

  isStateSilver(question: Question): boolean {
    return question.state === Action.SILVER;
  }

  isWithPicture(question: Question): boolean {
    return question.pictureUri !== undefined && question.pictureUri !== null;
  }

  show(question: Question): void {
    this.ws.send([
      {
        action: Action.OPEN,
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
      action: Action.SILVER,
      id: question.id
    }]);
  }

  setAnswered(question: Question): void {
    this.ws.send([
      {
        action: Action.ANSWERED,
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
      action: Action.CLOSE,
      id: question.id
    }]);
  }

  getPlayer(player: string): HTMLElement {
    return Util.getPlayer(player);
  }

  isWithSound(question: Question): boolean {
    return !!question.soundUri;
  }
}

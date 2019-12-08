import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Util} from '../../common/util';
import {WebsocketService} from '../../common/websocket.service';
import {Action} from '../../game-data/model/action';
import {Category} from '../../game-data/model/category';
import {Game} from '../../game-data/model/game';
import {Question} from '../../game-data/model/question';
import {Round} from '../../game-data/model/round';
import {Team} from '../../game-data/model/team';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit, OnChanges {

  @Input() game: Game;
  @Input() admin: boolean = false;

  currentAmount: number = 0;

  constructor(private ws: WebsocketService) {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    let result = 0;
    this.game.rounds.forEach((round: Round) => {
      round.categories.forEach((cat: Category) => {
        cat.questions.filter((que: Question) => {
          if (que.state === Action.OPEN) {
            result = que.amount;
          }
        });
      });
    });
    this.currentAmount = result;

  }

  plus(team: Team): void {
    this.ws.send({
      action: Action.SCORE,
      id: team.id,
      payload: this.currentAmount
    });
    this.currentAmount = 0;
  }

  minus(team: Team): void {
    this.ws.send({
      action: Action.SCORE,
      id: team.id,
      payload: -(this.currentAmount)
    });
    this.currentAmount = 0;
    this.resetButton();
  }

  getPlayer(player: string): HTMLElement {
    return Util.getPlayer(player);
  }

  resetButton(): void {
    this.ws.send({
      action: Action.RESET_BUTTON,
      id: -1
    });
  }

  toggleWinner(team: Team): void {
    this.ws.send({
      action: Action.TOGGLE_WINNER,
      id: team.id
    });
  }
}
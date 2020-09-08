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
          if (que.state === Action.QUESTION_OPEN) {
            result = que.amount;
          }
        });
      });
    });
    if (this.shouldDetectQuickest(changes)) {
      setTimeout(() => {
        // @ts-ignore
        this.getPlayer('ding').play();
        this.showQuickest();
      }, 1000);
    }
    this.currentAmount = result;
  }

  private shouldDetectQuickest(changes: SimpleChanges): boolean {
    return this.admin
      && changes['game']['currentValue']
      && changes['game']['previousValue']
      && this.getPressedCount(changes['game']['currentValue'].teams) === 1
      && this.getPressedCount(changes['game']['previousValue'].teams) === 0;
  }

  private getPressedCount(teams: Team[]): number {
    return teams.filter((team: Team) => team.havePressed).length;
  }

  showQuickest(): void {
    this.ws.send([{
      action: Action.SHOW_QUICKEST,
      id: -1
    }]);
  }

  plus(team: Team): void {
    this.ws.send([{
      action: Action.TEAM_SCORE,
      id: team.id,
      payload: this.currentAmount
    }]);
    this.currentAmount = 0;
  }

  minus(team: Team): void {
    this.ws.send([
      {
        action: Action.TEAM_SCORE,
        id: team.id,
        payload: -(this.currentAmount)
      },
      {
        action: Action.RESET_BUTTON,
        id: -1
      }
    ]);
    this.currentAmount = 0;
  }

  getPlayer(player: string): HTMLElement {
    return Util.getPlayer(player);
  }

  resetButton(): void {
    this.ws.send([{
      action: Action.RESET_BUTTON,
      id: -1
    }]);
  }

  toggleWinner(team: Team): void {
    this.ws.send([{
      action: Action.TOGGLE_WINNER,
      id: team.id
    }]);
  }
}

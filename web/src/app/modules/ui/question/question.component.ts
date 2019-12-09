import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {WebsocketService} from '../../common/websocket.service';
import {Action} from '../../game-data/model/action';
import {Category} from '../../game-data/model/category';
import {Game} from '../../game-data/model/game';
import {Round} from '../../game-data/model/round';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
})
export class QuestionComponent implements OnInit, OnChanges {

  @Input() game: Game;
  @Input() admin: boolean = false;
  activeRound: Round;

  constructor(private ws: WebsocketService) {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.activeRound = this.game.rounds
      .find((round: Round) => round.active);
  }

  showName(category: Category): void {
    this.ws.send([{
      action: Action.CATEGORY_SHOW,
      id: category.id
    }]);
  }

  hideName(category: Category): void {
    this.ws.send([{
      action: Action.CATEGORY_HIDE,
      id: category.id
    }]);
  }

  isShowCategoryName(category: Category): boolean {
    return category.nameState === Action.CATEGORY_SHOW;
  }

}

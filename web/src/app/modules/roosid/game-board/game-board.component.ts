import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Game} from "../model/game";
import {Util} from "../util/util";
import {Answer} from "../model/answer";

@Component({
  selector: 'app-game-board',
  templateUrl: './game-board.component.html',
  styleUrls: ['./game-board.component.css']
})
export class GameBoardComponent implements OnInit, OnChanges {

  @Input() game: Game;
  columnAnswers: Answer[] = [];

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const activeRound = Util.getActiveRound(this.game);
    if (activeRound) {
      this.columnAnswers = [...activeRound.answers];
      while (this.columnAnswers.length < 6) {
        this.columnAnswers.push(new Answer());
      }
    } else {
      this.columnAnswers = [
        new Answer(), new Answer(), new Answer(), new Answer(), new Answer(), new Answer()
      ];
    }
  }
}

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
  slotsPerColumn: number;
  firstColumnAnswers: Answer[] = [];
  secondColumnAnswers: Answer[] = [];

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const activeRound = Util.getActiveRound(this.game);
    if (activeRound) {
      this.slotsPerColumn = activeRound.answers.length / 2;
      this.firstColumnAnswers = activeRound.answers.slice(0, this.slotsPerColumn);
      this.secondColumnAnswers = activeRound.answers.slice(this.slotsPerColumn, activeRound.answers.length);
    } else {
      this.firstColumnAnswers = [
        new Answer(), new Answer(), new Answer()
      ];
      this.secondColumnAnswers = [
        new Answer(), new Answer(), new Answer()
      ];
    }
  }

  isAnswersPresent(): boolean {
    return this.firstColumnAnswers.length === 0 && this.secondColumnAnswers.length === 0;
  }

}

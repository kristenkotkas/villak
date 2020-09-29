import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Game} from "../model/game";
import {Round} from "../model/round";
import {Util} from "../util/util";

@Component({
  selector: 'app-scores',
  templateUrl: './scores.component.html',
  styleUrls: ['./scores.component.css']
})
export class ScoresComponent implements OnInit, OnChanges {

  @Input() game: Game;
  leftScore: number;
  rightScore: number;
  currentRoundScore: number;

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.game && this.game.teams) {
      if (this.game.teams[0]) {
        this.leftScore = this.game.teams[0].score;
      }
      if (this.game.teams[1]) {
        this.rightScore = this.game.teams[1].score;
      }
      const activeRound = Util.getActiveRound(this.game);
      if (activeRound) {
        this.currentRoundScore = activeRound.score;
      } else {
        this.currentRoundScore = 0;
      }
    }
  }

}

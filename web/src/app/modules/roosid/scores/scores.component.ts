import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Game} from "../model/game";
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
  leftTeamName: string;
  rightTeamName: string;

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.game && this.game.teams) {
      if (this.game.teams[0]) {
        this.leftScore = this.game.teams[0].score;
        this.leftTeamName = this.game.teams[0].name;
      }
      if (this.game.teams[1]) {
        this.rightScore = this.game.teams[1].score;
        this.rightTeamName = this.game.teams[1].name;
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

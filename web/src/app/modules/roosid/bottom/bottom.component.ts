import {Component, Input, OnInit} from '@angular/core';
import {Game} from "../model/game";
import {Util} from "../util/util";

@Component({
  selector: 'app-bottom',
  templateUrl: './bottom.component.html',
  styleUrls: ['./bottom.component.css']
})
export class BottomComponent implements OnInit {

  @Input() game: Game;

  constructor() {
  }

  ngOnInit(): void {
  }

  getMultiplayerString(game: Game): string {
    const activeRound = Util.getActiveRound(game);
    if (activeRound) {
      return `${activeRound.multiplayer} x`;
    }
    return '';
  }

  getTeamCrossesCount(teamIndex: number): number {
    if (this.game && this.game.teams && this.game.teams[teamIndex]) {
      return this.game.teams[teamIndex].crossCount;
    }
    return 0;
  }

}

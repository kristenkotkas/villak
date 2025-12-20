import {Component, OnInit} from "@angular/core";
import {DataService} from "../data/data.service";
import {Game} from "../model/game";
import {Round} from "../model/round";
import {Util} from "../util/util";

@Component({
  selector: 'app-host',
  templateUrl: './host.component.html',
  styleUrls: ['./host.component.css']
})
export class HostComponent implements OnInit {

  game: Game;
  activeRound: Round;

  constructor(
    private dataService: DataService
  ) {

  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe((game: Game) => {
      this.game = game;
      this.activeRound = this.getActiveRound(this.game);
      console.log(this.activeRound);
    });
  }

  private getActiveRound(game: Game): Round {
    return Util.getActiveRound(game);
  }

}

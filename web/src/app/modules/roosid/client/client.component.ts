import {Component, OnInit} from '@angular/core';
import {Game} from "../model/game";
import {DataService} from "../data/data.service";

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  game: Game;

  constructor(private dataService: DataService) {
  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe((game: Game) => {
      this.game = game;
      console.log(this.game);
    });
  }

}

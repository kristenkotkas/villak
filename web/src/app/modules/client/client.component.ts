import {Component, OnInit} from '@angular/core';
import {DataService} from '../common/data.service';
import {Game} from '../game-data/model/game';

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
    this.dataService.currentData.subscribe(data => this.game = data);
  }

}

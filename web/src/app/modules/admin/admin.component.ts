import {Component, OnInit} from '@angular/core';
import {DataService} from '../common/data.service';
import {WebsocketService} from '../common/websocket.service';
import {Game} from '../game-data/model/game';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  game: Game;

  constructor(private dataService: DataService,
              private ws: WebsocketService) {
  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => this.game = data);
  }
}

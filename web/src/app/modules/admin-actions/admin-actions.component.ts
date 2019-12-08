import {Component, Input, OnInit} from '@angular/core';
import {DataService} from '../common/data.service';
import {Util} from '../common/util';
import {WebsocketService} from '../common/websocket.service';
import {Action} from '../game-data/model/action';
import {Game} from '../game-data/model/game';

@Component({
  selector: 'app-admin-actions',
  templateUrl: './admin-actions.component.html',
  styleUrls: ['./admin-actions.component.css']
})
export class AdminActionsComponent implements OnInit {

  @Input() game: Game;

  constructor(private ws: WebsocketService) {
  }

  ngOnInit(): void {
  }

  setActiveRound(roundId: number): void {
    this.ws.send({
      action: Action.ACTIVE_ROUND,
      id: roundId
    });
  }

  stop(playerName: string, src: string): void {
    const player: HTMLElement = this.getPlayer(playerName);
    player.setAttribute('src', '');
    player.setAttribute('src', src);
  }

  resetButton(): void {
    this.ws.send({
      action: Action.RESET_BUTTON,
      id: -1
    });
  }

  showQuickest(): void {
    this.ws.send({
      action: Action.SHOW_QUICKEST,
      id: -1
    });
  }

  getPlayer(player: string): HTMLElement {
    return Util.getPlayer(player);
  }

}

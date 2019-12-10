import {Component, Input, OnInit} from '@angular/core';
import {Game} from '../game-data/model/game';
import {WebsocketService} from "../common/websocket.service";
import {Util} from "../common/util";
import {Action} from "../game-data/model/action";
import {LocalStorageUtil} from "../common/local-storage-util";

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  @Input() game: Game;
  private deviceId: number = Util.getDeviceId();

  constructor(private ws: WebsocketService) {
  }

  ngOnInit(): void {
    if (this.game.settings.gameDeviceId === null) {
      this.ws.send([{action: Action.SET_CLIENT_DEVICE_ID, id: -1, payload: this.deviceId}]);
      LocalStorageUtil.write('gameDeviceId', this.deviceId.toString());
    }
  }

}

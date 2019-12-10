import {Component, Input, OnInit} from '@angular/core';
import {Game} from '../game-data/model/game';
import {WebsocketService} from "../common/websocket.service";
import {Action} from "../game-data/model/action";
import {Util} from "../common/util";
import {LocalStorageUtil} from "../common/local-storage-util";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  @Input() game: Game;
  private deviceId: number = Util.getDeviceId();

  constructor(private ws: WebsocketService) {
  }

  ngOnInit(): void {
    if (this.game.settings.adminDeviceId === null) {
      this.ws.send([{action: Action.SET_ADMIN_DEVICE_ID, id: -1, payload: this.deviceId}]);
      LocalStorageUtil.write('adminDeviceId', this.deviceId.toString());
    }
  }

}

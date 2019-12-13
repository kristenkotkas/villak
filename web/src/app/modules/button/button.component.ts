import {Component, OnInit} from '@angular/core';
import {DataService} from '../common/data.service';
import {WebsocketService} from '../common/websocket.service';
import {Action} from '../game-data/model/action';
import {Game} from '../game-data/model/game';
import {Team} from '../game-data/model/team';
import {LocalStorageUtil} from "../common/local-storage-util";

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements OnInit {

  selectedTeam: Team = undefined;
  candidateTeam: Team = undefined;
  teams: Team[];
  syncTime: number = undefined;

  constructor(private dataService: DataService,
              private ws: WebsocketService) {
  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe((data: Game) => {
      this.teams = data.teams;
      let found: Team = this.teams.find((team: Team) =>
        team.deviceId === parseInt(LocalStorageUtil.read(LocalStorageUtil.KEY_DEVICE_ID), 10));
      if (found !== undefined) {
        this.selectedTeam = found;
        this.syncTime = found.timeSynced;
      }
    });
  }

  getUnselectedTeams(): Team[] {
    return this.teams.filter((team: Team) => team.deviceId === null);
  }

  isShowSyncButton(): boolean {
    return !this.isSynced();
  }

  isSynced(): boolean {
    return this.syncTime !== undefined;
  }

  isTeamSelected(): boolean {
    return !!this.selectedTeam;
  }

  selectCandidateTeam(team: Team): void {
    this.candidateTeam = team;
  }

  isCandidateSelected(): boolean {
    return !!this.candidateTeam;
  }

  sync(): void {
    this.syncTime = Date.now();
  }

  isShowButton(): boolean {
    return this.isTeamSelected();
  }

  selectTeam(): void {
    this.ws.send([
      {
        action: Action.SET_TEAM_DEVICE_ID,
        id: this.candidateTeam.id,
        payload: parseInt(LocalStorageUtil.read(LocalStorageUtil.KEY_DEVICE_ID), 10)
      },
      {
        action: Action.SYNC_BUTTON,
        id: this.candidateTeam.id,
        payload: this.syncTime
      }
    ]);
  }

  answer(): void {
    if (!this.selectedTeam.havePressed) {
      this.ws.send([{
        action: Action.PRESS_BUTTON,
        id: this.selectedTeam.id,
        payload: Date.now()
      }]);
    }
  }
}

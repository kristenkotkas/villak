import {Component, OnInit} from '@angular/core';
import {DataService} from '../common/data.service';
import {WebsocketService} from '../common/websocket.service';
import {Action} from '../game-data/model/action';
import {Game} from '../game-data/model/game';
import {Team} from '../game-data/model/team';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements OnInit {

  selectedTeam: Team = undefined;
  teams: Team[];
  selectClicked: boolean = false;

  constructor(private dataService: DataService,
              private ws: WebsocketService) {
  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe((data: Game) => {
      this.teams = data.teams;
    });
  }

  isTeamSelected(): boolean {
    return !!this.selectedTeam;
  }

  selectTeam(team: Team): void {
    this.selectedTeam = team;
  }

  isShowButton(): boolean {
    return this.selectClicked && this.isTeamSelected();
  }

  onValiClicked(): void {
    this.selectClicked = true;
  }

  answer(): void {
    if (!this.selectedTeam.havePressed) {
      this.ws.send({
        action: Action.PRESS_BUTTON,
        id: this.selectedTeam.id,
        payload: Date.now()
      });
    }
  }
}

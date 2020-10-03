import {Component, OnInit} from '@angular/core';
import {Util} from "../../common/util";
import {DataService} from "../data/data.service";
import {Action} from "../model/action";
import {Game} from "../model/game";
import {Message} from "../model/message";

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
      this.game.latestMessages.forEach((message: Message) => this.handleSoundEvent(message));
    });
  }

  private handleSoundEvent(message: Message): void {
    if (message.action === Action.TOGGLE_ANSWER) {
      this.playSound('roosid_correct');
    } else if (message.action === Action.ADD_CROSS) {
      this.playSound('roosid_wrong');
    } else if (message.action === Action.PLAY_SHORT_THEME) {
      this.playSound('roosid_round_winner');
    } else if (message.action === Action.STOP_SHORT_THEME) {
      this.stopSound('roosid_round_winner');
    } else if (message.action === Action.PLAY_INTRO) {
      this.playSound('roosid_theme');
    } else if (message.action === Action.STOP_INTRO) {
      this.stopSound('roosid_theme');
    }
  }

  private playSound(sound: string): void {
    // @ts-ignore
    Util.getPlayer(sound).play();
  }

  private stopSound(sound: string): void {
    const player: HTMLElement = Util.getPlayer(sound);
    player.setAttribute('src', '');
    player.setAttribute('src', `../../../../assets/sounds/roosid/${sound}.mp3`);
  }

}

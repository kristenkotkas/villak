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
  blockHeights: BlockSizes = {
    buffer: 0,
    score: 15,
    board: 70,
    bottom: 15
  };

  constructor(private dataService: DataService) {
  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe((game: Game) => {
      this.game = game;
      console.log(this.game);
      this.calculateHeights();
      this.game.latestMessages.forEach((message: Message) => this.handleSoundEvent(message));
    });
  }

  calculateHeights(): void {
    let fullHeight = 100;
    if (this.game.settings) {
      this.blockHeights.buffer = this.game.settings.bufferSize;
      fullHeight -= this.game.settings.bufferSize;
    }
    this.blockHeights.score = 0.15 * fullHeight;
    this.blockHeights.bottom = 0.15 * fullHeight;
    this.blockHeights.board = 100 - (this.blockHeights.buffer + this.blockHeights.score + this.blockHeights.bottom);
    console.log(this.blockHeights);
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

interface BlockSizes {
  buffer: number;
  score: number;
  board: number;
  bottom: number;
}

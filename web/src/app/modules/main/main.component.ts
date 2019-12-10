import {Component, OnInit} from '@angular/core';
import {Game} from "../game-data/model/game";
import {Question} from "../game-data/model/question";
import {DataService} from "../common/data.service";
import {Round} from "../game-data/model/round";
import {Category} from "../game-data/model/category";
import {GameMode} from "../common/game-mode";
import {LocalStorageUtil} from "../common/local-storage-util";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  game: Game;
  audioQuestions: Question[] = [];
  isAdmin: boolean = false;
  isClient: boolean = false;
  isButton: boolean = false;
  gameMode: typeof GameMode = GameMode;

  constructor(private dataService: DataService) {
  }

  ngOnInit(): void {
    this.dataService.currentData.subscribe(data => {
      this.game = data;
      if (this.audioQuestions.length === 0) {
        this.initAudio(data);
      }
    });
  }

  initAudio(game: Game): void {
    game.rounds.forEach((round: Round) => {
      round.categories.forEach((category: Category) => {
        category.questions.forEach((question: Question) => {
          if (!!question.soundUri) {
            this.audioQuestions.push(question);
          }
        });
      });
    });
  }

  isShowAdminBtn(): boolean {
    return !this.isModeSelected() &&
      (!!this.game && !!this.game.settings && (this.game.settings.adminDeviceId === null ||
        this.game.settings.adminDeviceId.toString() === LocalStorageUtil.read('adminDeviceId')));
  }

  isShowClientBtn(): boolean {
    return !this.isModeSelected() &&
      (!!this.game && !!this.game.settings && (this.game.settings.gameDeviceId === null ||
        this.game.settings.gameDeviceId.toString() === LocalStorageUtil.read('gameDeviceId')));
  }

  isShowButtonBtn(): boolean {
    return !this.isModeSelected();
  }

  isModeSelected(): boolean {
    return this.isAdmin || this.isClient || this.isButton;
  }

  setMode(gameMode: GameMode): void {
    this.isAdmin = gameMode === this.gameMode.ADMIN;
    this.isClient = gameMode === this.gameMode.CLIENT;
    this.isButton = gameMode === this.gameMode.BUTTON;
  }
}

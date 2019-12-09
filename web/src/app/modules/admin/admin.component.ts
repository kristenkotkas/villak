import {Component, OnInit} from '@angular/core';
import {DataService} from '../common/data.service';
import {Game} from '../game-data/model/game';
import {Question} from "../game-data/model/question";
import {Round} from "../game-data/model/round";
import {Category} from "../game-data/model/category";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  game: Game;
  audioQuestions: Question[] = [];

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
}

import {Component, Input, OnInit} from '@angular/core';
import {BlockSizes} from "../client/client.component";
import {FastMoneyPlayerResponse} from "../model/fast-money-player-response";
import {FastMoneyQuestion} from "../model/fast-money-question";
import {Game} from "../model/game";

@Component({
  selector: 'app-fast-money',
  templateUrl: './fast-money.component.html',
  styleUrls: ['./fast-money.component.css']
})
export class FastMoneyComponent implements OnInit {

  @Input() game: Game;
  @Input() blockHeights: BlockSizes;

  constructor() {
  }

  ngOnInit(): void {
  }

  getQuestion(index: number): FastMoneyQuestion {
    return this.game.fastMoney.questions[index];
  }

  getTotalResponse(): FastMoneyPlayerResponse {
    return {
      id: -1,
      answer: 'Kokku',
      score: this.game.fastMoney.currentScore,
      questionVisible: true
    };
  }

}

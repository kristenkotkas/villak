import {Component, HostBinding, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FastMoneyPlayerResponse} from "../model/fast-money-player-response";
import {Util} from "../util/util";

@Component({
  selector: 'app-fast-money-answer',
  templateUrl: './fast-money-answer.component.html',
  styleUrls: ['./fast-money-answer.component.css']
})
export class FastMoneyAnswerComponent implements OnInit, OnChanges {

  @Input() response: FastMoneyPlayerResponse;
  @Input() hidden: boolean;
  @Input() valuesHidden: boolean;
  visibleAnswer: string = '';

  @HostBinding('class') hostClasses = 'col';

  constructor() {
  }

  ngOnInit(): void {
  }

  shouldBeBlinking(): boolean {
    return this.response.questionVisible && !Util.isPresent(this.response.score);
  }

  ngOnChanges(changes: SimpleChanges): void {
    const responseChange = changes['response'];
    let interval;
    if (responseChange && responseChange.currentValue && responseChange.currentValue.answer) {
      if (this.visibleAnswer === '') {
        if (responseChange.currentValue.questionVisible) {
          //console.log('run');
          interval = setInterval(() => {
            this.visibleAnswer = responseChange.currentValue.answer.substring(0, this.visibleAnswer.length + 1);
            //console.log('added letter');
            if (this.visibleAnswer === responseChange.currentValue.answer) {
              clearInterval(interval);
              //console.log('cleared');
            }
          }, 70);
        }
      }
      if (!responseChange.currentValue.questionVisible) {
        clearInterval(interval);
        this.visibleAnswer = '';
      }
    }
    if (responseChange && responseChange.currentValue && Util.isPresent(responseChange.currentValue.score)
      && responseChange.previousValue && responseChange.previousValue.score !== responseChange.currentValue.score) {
      console.log(responseChange.currentValue);
      if (responseChange.currentValue.score === 0) {
        // @ts-ignore
        Util.getPlayer('roosid_fast_money_zero').play();
      } else {
        // @ts-ignore
        Util.getPlayer('roosid_fast_money_points').play();
      }
    }
  }

}

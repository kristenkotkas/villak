import {Component, HostBinding, Input, OnInit} from '@angular/core';
import {Answer} from "../model/answer";
import {AnswerState} from "../model/answer-state";

@Component({
  selector: 'app-answer-card',
  templateUrl: './answer-card.component.html',
  styleUrls: ['./answer-card.component.css']
})
export class AnswerCardComponent implements OnInit {

  @Input() answer: Answer;

  @HostBinding('class') hostClasses = 'col';

  constructor() {
  }

  ngOnInit(): void {
    console.log('inited answer', this.answer);
  }

  isOpened(): boolean {
    return this.answer.state === AnswerState.OPENED;
  }

  isClosed(): boolean {
    return this.answer.state === AnswerState.CLOSED;
  }

}

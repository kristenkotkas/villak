import {Component, HostBinding, OnInit, Input} from '@angular/core';

@Component({
  selector: 'app-cross-card',
  templateUrl: './cross-card.component.html',
  styleUrls: ['./cross-card.component.css']
})
export class CrossCardComponent implements OnInit {

  @HostBinding('class') hostClasses = 'col';
  @Input() currentCross: number;
  @Input() totalCrosses: number;

  constructor() {
  }

  ngOnInit(): void {
  }

  isOpened(): boolean {
    return this.currentCross <= this.totalCrosses;
  }

}

import {Component, Input, OnInit, HostBinding} from '@angular/core';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  @Input() value: string;
  @Input() colored: boolean = false;
  @Input() red: boolean = false;

  @HostBinding('class') class = 'col retro-card';

  constructor() {
  }

  ngOnInit(): void {
  }

}

import {Component, HostBinding, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-team-card',
  templateUrl: './team-card.component.html',
  styleUrls: ['./team-card.component.css']
})
export class TeamCardComponent implements OnInit {

  @HostBinding('class') hostClasses = 'col';

  @Input() value: string;
  @Input() colored: boolean = false;

  constructor() {
  }

  ngOnInit(): void {
  }

}

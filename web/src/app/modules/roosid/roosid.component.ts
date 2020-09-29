import {Component} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-roosid',
  styleUrls: ['./roosid.component.css'],
  templateUrl: './roosid.component.html'
})
export class RoosidComponent {

  constructor(private router: Router) {
  }

  route(url: string): void {
    this.router.navigate([url]);
  }

}

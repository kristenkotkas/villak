import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {RoosidComponent} from "./roosid.component";
import { AdminComponent } from './admin/admin.component';
import { ClientComponent } from './client/client.component';
import { RouterModule } from '@angular/router';
import {DataService} from "./data/data.service";
import {WebsocketService} from "./data/websocket.service";
import { ScoresComponent } from './scores/scores.component';
import { CardComponent } from './ui/card/card.component';
import { GameBoardComponent } from './game-board/game-board.component';
import { BottomComponent } from './bottom/bottom.component';
import { AnswerCardComponent } from './answer-card/answer-card.component';
import { CrossCardComponent } from './cross-card/cross-card.component';

@NgModule({
  declarations: [
    RoosidComponent,
    AdminComponent,
    ClientComponent,
    ScoresComponent,
    CardComponent,
    GameBoardComponent,
    BottomComponent,
    AnswerCardComponent,
    CrossCardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    RouterModule
  ],
  providers: [
    DataService,
    WebsocketService
  ]
})
export class RoosidModule {
}

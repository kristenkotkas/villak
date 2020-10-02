import {CommonModule} from '@angular/common';
import {HttpClientModule} from "@angular/common/http";
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {AdminComponent} from './admin/admin.component';
import {AnswerCardComponent} from './answer-card/answer-card.component';
import {BottomComponent} from './bottom/bottom.component';
import {ClientComponent} from './client/client.component';
import {CrossCardComponent} from './cross-card/cross-card.component';
import {DataService} from "./data/data.service";
import {WebsocketService} from "./data/websocket.service";
import {GameBoardComponent} from './game-board/game-board.component';
import {RoosidComponent} from "./roosid.component";
import {ScoresComponent} from './scores/scores.component';
import {CardComponent} from './ui/card/card.component';
import { EditorComponent } from './editor/editor.component';
import { TeamCardComponent } from './team-card/team-card.component';

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
    CrossCardComponent,
    EditorComponent,
    TeamCardComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [
    DataService,
    WebsocketService
  ]
})
export class RoosidModule {
}

import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {VillakComponent} from "./villak.component";
import {AdminComponent} from "./admin/admin.component";
import {ClientComponent} from "./client/client.component";
import {GameBoardComponent} from "./game-board/game-board.component";
import {TeamComponent} from "./team/team.component";
import {QuestionComponent} from "./question/question.component";
import {ButtonComponent} from "./button/button.component";
import {AdminActionsComponent} from "./admin-actions/admin-actions.component";
import {MainComponent} from "./main/main.component";
import {DataService} from "./common/data.service";
import {WebsocketService} from "./common/websocket.service";

@NgModule({
  declarations: [
    VillakComponent,
    AdminComponent,
    ClientComponent,
    GameBoardComponent,
    TeamComponent,
    QuestionComponent,
    ButtonComponent,
    AdminActionsComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule
  ],
  bootstrap: [
    VillakComponent
  ],
  providers: [
    DataService,
    WebsocketService
  ]
})
export class VillakModule {
}

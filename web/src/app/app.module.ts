import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {AdminActionsComponent} from './modules/admin-actions/admin-actions.component';
import {AdminComponent} from './modules/admin/admin.component';
import {ButtonComponent} from './modules/button/button.component';
import {ClientComponent} from './modules/client/client.component';
import {GameBoardComponent} from './modules/ui/game-board/game-board.component';
import {QuestionComponent} from './modules/ui/question/question.component';
import {TeamComponent} from './modules/ui/team/team.component';
import {MainComponent} from './modules/main/main.component';

@NgModule({
  declarations: [
    AppComponent,
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
    CommonModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {AdminActionsComponent} from './modules/admin-actions/admin-actions.component';
import {AdminComponent} from './modules/admin/admin.component';
import {ButtonComponent} from './modules/button/button.component';
import {ClientComponent} from './modules/client/client.component';
import {GameBoardComponent} from './modules/ui/game-board/game-board.component';
import {QuestionComponent} from './modules/ui/question/question.component';
import {TeamComponent} from './modules/ui/team/team.component';
import { MainComponent } from './modules/main/main.component';

const appRoutes: Routes = [
  {path: '', component: MainComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'client', component: ClientComponent},
  {path: 'button', component: ButtonComponent},
];

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
    RouterModule.forRoot(appRoutes),
    CommonModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

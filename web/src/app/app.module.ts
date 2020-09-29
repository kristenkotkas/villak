import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {RouterModule} from '@angular/router';
import {VillakModule} from "./modules/villak/villak.module";
import {RoosidModule} from "./modules/roosid/roosid.module";

@NgModule({
  imports: [
    VillakModule,
    RoosidModule,
    BrowserModule,
    CommonModule,
    FormsModule,
    RouterModule,
    AppRoutingModule // must be last in the array
  ],
  declarations: [
    AppComponent,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

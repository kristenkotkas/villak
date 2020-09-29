import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {VillakComponent} from "./modules/villak/villak.component";
import {RoosidComponent} from "./modules/roosid/roosid.component";
import {AdminComponent} from "./modules/roosid/admin/admin.component";
import {ClientComponent} from "./modules/roosid/client/client.component";

const appRoutes: Routes = [
  {
    path: 'villak',
    component: VillakComponent
  }, {
    path: 'roosid',
    component: RoosidComponent,
  },
  {
    path: 'roosid/admin',
    component: AdminComponent
  },
  {
    path: 'roosid/client',
    component: ClientComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ]
})
export class AppRoutingModule {
}

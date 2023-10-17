import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePage } from './home.page';
import { DetailsComponent } from "../details/details.component";
import { ModifyComponent } from "../modify/modify.component";

const routes: Routes = [
  {
    path: '',
    component: HomePage,
  },
  {
    path: ':id',
    component: DetailsComponent,
  },
  {
    path: ':id/:action',
    component: ModifyComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomePageRoutingModule {}

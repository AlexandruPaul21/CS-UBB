import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePage } from './home.page';
import { DetailsComponent } from "../details/details.component";
import { ModifyComponent } from "../modify/modify.component";
import { NewFlightComponent } from "../new-flight/new-flight.component";
import { PhotoComponent } from "../photo/photo.component";

const routes: Routes = [
  {
    path: '',
    component: HomePage,
  },
  {
    path: 'photo',
    component: PhotoComponent
  },
  {
    path: 'new',
    component: NewFlightComponent,
  },
  {
    path: ':id',
    component: DetailsComponent,
  },
  {
    path: ':id/:action',
    component: ModifyComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomePageRoutingModule {
}

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FlightTableComponentComponent } from "./flight-table-component/flight-table-component.component";
import { FlightFormComponent } from "./flight-form/flight-form.component";
import {FlightEditFormComponent} from "./flight-edit-form/flight-edit-form.component";

const routes: Routes = [
  { path: 'flights', component: FlightTableComponentComponent },
  { path: 'create-flight', component: FlightFormComponent },
  { path: 'edit-flight/:id', component: FlightEditFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

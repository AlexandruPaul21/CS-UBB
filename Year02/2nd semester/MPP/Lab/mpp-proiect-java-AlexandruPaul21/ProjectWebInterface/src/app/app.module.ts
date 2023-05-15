import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { FlightTableComponentComponent } from './flight-table-component/flight-table-component.component';
import { AppRoutingModule } from './app-routing.module';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import { FlightFormComponent } from './flight-form/flight-form.component';
import { FlightEditFormComponent } from './flight-edit-form/flight-edit-form.component';

@NgModule({
  declarations: [
    AppComponent,
    FlightTableComponentComponent,
    FlightFormComponent,
    FlightEditFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgForOf,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { HomePage } from './home.page';

import { HomePageRoutingModule } from './home-routing.module';
import { DetailsComponent } from "../details/details.component";
import { ModifyComponent } from "../modify/modify.component";
import { NewFlightComponent } from "../new-flight/new-flight.component";
import { LoginComponent } from "../login/login.component";


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    HomePageRoutingModule
  ],
  declarations: [HomePage, DetailsComponent, ModifyComponent, NewFlightComponent, LoginComponent]
})
export class HomePageModule {}

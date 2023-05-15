import { Component } from '@angular/core';
import {Flight} from "../../common-module/model/Flight";
import {FlightServiceService} from "../../common-module/services/flight-service.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-flight-edit-form',
  templateUrl: './flight-edit-form.component.html',
  styleUrls: ['./flight-edit-form.component.css']
})
export class FlightEditFormComponent {

  public flight: Flight;
  private id: string | null;

  constructor(
    private flightService: FlightServiceService,
    private activeRoute : ActivatedRoute,
  ) {
    this.flight = new Flight(0, "", new Date(""), "", 0);
    this.id = this.activeRoute.snapshot.paramMap.get('id');
    if (this.id != null) {
      this.flightService.findOne(this.id).subscribe(data => {
        this.flight = data;
      })
    } else {
      console.log("Error, not found");
    }
  }

  onSubmit() {
    this.flightService.updateFlight(this.flight).subscribe((data) => {
      if (data != null) {
        alert("Flight updated successfully!");
      }
    });
  }
}

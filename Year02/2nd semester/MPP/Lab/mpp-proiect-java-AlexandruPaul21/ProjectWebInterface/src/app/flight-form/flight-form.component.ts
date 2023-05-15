import { Component } from '@angular/core';
import { Flight } from "../../common-module/model/Flight";
import { FormsModule } from "@angular/forms";
import {FlightServiceService} from "../../common-module/services/flight-service.service";

@Component({
  selector: 'app-flight-form',
  templateUrl: './flight-form.component.html',
  styleUrls: ['./flight-form.component.css']
})
export class FlightFormComponent {
  flight: Flight;

  constructor(
    private flightService: FlightServiceService,
  ) {
    this.flight = new Flight(0, "", new Date(""), "", 0);
  }

  onSubmit() {
    this.flightService.addFlight(this.flight).subscribe(
      (response) => {
        if (response != null) {
          alert("Flight added successfully!");
        }
      }
    );
  }
}

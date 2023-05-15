import { Component, OnInit } from '@angular/core';
import { FlightServiceService } from "../../common-module/services/flight-service.service";
import { Flight } from "../../common-module/model/Flight";

@Component({
  selector: 'app-flight-table-component',
  templateUrl: './flight-table-component.component.html',
  styleUrls: ['./flight-table-component.component.css']
})
export class FlightTableComponentComponent implements OnInit {

  flights: Flight[] = [];
  title: string = "Flight table";

  constructor(
    private flightService: FlightServiceService,
  ) {

  }

  ngOnInit(): void {
    this.flightService.findAll().subscribe(data => {
      this.flights = data;
    })
  }


  onDeletePressed(id: number | undefined) {
    if (id == undefined) {
      return;
    }
    this.flightService.delete(id).subscribe(data => {
      if (data != null) {
        this.ngOnInit();
      }
    });
  }
}

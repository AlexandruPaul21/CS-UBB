import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { Flight } from "../model/flight";
import { FlightService } from "../service/flight.service";

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss'],
})
export class DetailsComponent  implements OnInit {
  public flight: Flight | null = null;

  constructor(private activeRoute: ActivatedRoute, private flightService: FlightService) {
    const id = this.activeRoute.snapshot.paramMap.get('id');
    if (id == null) return;

    this.flightService.findOne(Number(id)).subscribe((flight) => {
      this.flight = flight;
    });
  }

  ngOnInit() {}

}

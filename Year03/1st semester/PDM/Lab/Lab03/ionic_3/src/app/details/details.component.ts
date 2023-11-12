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
  private readonly id: string | null;

  constructor(
    private activeRoute: ActivatedRoute,
    private flightService: FlightService,
    ) {
    this.id = this.activeRoute.snapshot.paramMap.get('id');
  }

  public ngOnInit() {
      if (this.id == null) return;
      const id = Number(this.id);
      this.flightService.findAll().subscribe((flights) => {
          this.flight = flights.filter((flight) => flight.id == id)[0] ?? null;
      });
  }
}

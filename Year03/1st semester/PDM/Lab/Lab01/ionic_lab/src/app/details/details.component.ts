import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { Flight } from "../model/flight";
import { FlightService } from "../service/flight.service";
import { Network } from "@capacitor/network";

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss'],
})
export class DetailsComponent  implements OnInit {
  public flight: Flight | null = null;
  private readonly id: string | null;
  public networkStatus: boolean = true;

  constructor(
    private activeRoute: ActivatedRoute,
    private flightService: FlightService
    ) {
    this.id = this.activeRoute.snapshot.paramMap.get('id');
    Network.addListener('networkStatusChange', status => {
      this.networkStatus = status.connected;
    });
  }

  public async ngOnInit() {
    this.networkStatus = (await Network.getStatus()).connected;
    if (this.id == null) return;
    (await this.flightService.findAll()).subscribe((flights) => {
      this.flight = flights.find((flight) => this.id === String(flight._id))!!;
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { Flight } from "../model/flight";
import { FlightService } from "../service/flight.service";
import { RxStompService } from "../service/stomp.service";

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
    private rxStompService: RxStompService
    ) {
    this.id = this.activeRoute.snapshot.paramMap.get('id');

    this.rxStompService.watch('/topic/flights').subscribe(() => {
      this.ngOnInit();
    })
  }

  public ngOnInit() {
      if (this.id == null) return;
      this.flightService.findOne(Number(this.id)).subscribe((flight) => {
          this.flight = flight;
      });
  }
}

import { Component, OnDestroy } from '@angular/core';
import { Flight } from "../model/flight";
import { FlightService } from "../service/flight.service";
import { Subscription } from "rxjs";
import { RxStompService } from "../service/stomp.service";

@Component({
  selector: 'app-new-flight',
  templateUrl: './new-flight.component.html',
  styleUrls: ['./new-flight.component.scss'],
})
export class NewFlightComponent  implements OnDestroy {
  public flight: Flight;
  private subscription: Subscription | null = null;

  public constructor(private flightService: FlightService, private rxStompService: RxStompService) {
    this.flight = new Flight();
  }

  public ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  public saveFlight() {
    this.subscription = this.flightService.save(this.flight).subscribe(() => {
      this.rxStompService.publish({ destination: "/topic/flights", body: "NEW ENTITY"});
    });
  }
}

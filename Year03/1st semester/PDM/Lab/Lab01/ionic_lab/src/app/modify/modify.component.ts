import { Component, OnDestroy, OnInit } from '@angular/core';
import { Flight } from "../model/flight";
import { ActivatedRoute } from "@angular/router";
import { FlightService } from "../service/flight.service";
import { Subscription } from "rxjs";
import { RxStompService } from "../service/stomp.service";

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.scss'],
})
export class ModifyComponent  implements OnInit, OnDestroy {
  public flight: Flight;
  public date: Date = new Date();
  public isDeleteAction: boolean = false;
  private subscription: Subscription | null = null;
  private readonly id: string | null;

  constructor(
    private activeRoute: ActivatedRoute,
    private flightService: FlightService,
    private rxStompService: RxStompService
  ) {
    this.flight = {
      id: 0,
      destination: '',
      plane: '',
      estimatedDeparture: new Date(),
      canceled: false
    };
    this.id = this.activeRoute.snapshot.paramMap.get('id');
    this.isDeleteAction = this.activeRoute.snapshot.paramMap.get('action') === 'delete';

    this.rxStompService.watch('/topic/flights').subscribe(() => {
      this.ngOnInit();
    });
  }

  public ngOnInit() {
      if (this.id == null) return;
      this.flightService.findOne(Number(this.id)).subscribe((flight) => {
          this.flight = flight;
      });
  }

  public ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  public updateFlight() {
    this.subscription = this.flightService.update(this.flight.id!!, this.flight).subscribe(() => {
        this.rxStompService.publish({ destination: "/topic/flights", body: "UPDATE"});
    });
  }

  public deleteFlight() {
    this.subscription = this.flightService.del(this.flight.id!!).subscribe(() => {
        this.rxStompService.publish({ destination: "/topic/flights", body: "DELETE"});
    });
  }
}

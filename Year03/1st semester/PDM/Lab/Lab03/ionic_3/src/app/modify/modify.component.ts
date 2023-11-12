import { Component, OnDestroy, OnInit } from '@angular/core';
import { Flight } from "../model/flight";
import { ActivatedRoute } from "@angular/router";
import { FlightService } from "../service/flight.service";
import { Subscription } from "rxjs";

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.scss'],
})
export class ModifyComponent implements OnInit, OnDestroy {
  public flight: Flight;
  public date: Date = new Date();
  public isDeleteAction: boolean = false;
  private subscription: Subscription | null = null;
  private readonly id: string | null;

  constructor(
    private activeRoute: ActivatedRoute,
    private flightService: FlightService,
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
  }

  public ngOnInit() {
    if (this.id == null) return;
    const id = Number(this.id);
    this.flightService.findAll().subscribe((flights) => {
      this.flight = flights.filter((flight) => flight.id == id)[0] ?? null;
    });
  }

  public ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  public updateFlight() {
    this.subscription = this.flightService.update(this.flight.id!!, this.flight).subscribe(() => {
    });
  }

  public deleteFlight() {
    this.subscription = this.flightService.del(this.flight.id!!).subscribe(() => {
    });
  }
}

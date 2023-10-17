import { Component, OnInit } from '@angular/core';
import { Flight } from "../model/flight";
import { ActivatedRoute } from "@angular/router";
import { FlightService } from "../service/flight.service";

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.scss'],
})
export class ModifyComponent  implements OnInit {
  public flight: Flight;
  public date: Date = new Date();
  public isDeleteAction: boolean = false;

  constructor(private activeRoute: ActivatedRoute, private flightService: FlightService) {
    this.flight = {
      id: 0,
      destination: '',
      plane: '',
      estimatedDeparture: new Date(),
      canceled: false
    };
    const id = this.activeRoute.snapshot.paramMap.get('id');
    this.isDeleteAction = this.activeRoute.snapshot.paramMap.get('action') === 'delete';
    if (id == null) return;

    this.flightService.findOne(Number(id)).subscribe((flight) => {
      this.flight = flight;
    });
  }

  ngOnInit() {}

  protected readonly console = console;
}

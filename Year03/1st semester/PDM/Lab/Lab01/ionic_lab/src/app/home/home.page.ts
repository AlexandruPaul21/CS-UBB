import { Component, OnInit } from '@angular/core';
import { FlightService } from "../service/flight.service";
import { Flight } from "../model/flight";
import { Router } from "@angular/router";
import { RxStompService } from "../service/stomp.service";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  public flights: Flight[] = [];

  constructor(
    private flightService: FlightService,
    public router: Router,
    private rxStompService: RxStompService
  ) {
    this.rxStompService.watch('/topic/flights').subscribe(() => {
      console.log("Here");
      this.ngOnInit();
    })
  }

  public ngOnInit(): void {
    this.flightService.findAll().subscribe((flights) => {
      this.flights = flights;
    })
  }


  protected readonly console = console;
}

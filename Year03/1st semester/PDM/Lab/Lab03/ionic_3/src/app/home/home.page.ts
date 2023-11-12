import { Component, OnInit } from '@angular/core';
import { FlightService } from "../service/flight.service";
import { Flight } from "../model/flight";
import { Router } from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  public flights: Flight[] = [];
  private ws: WebSocket;

  constructor(
    private flightService: FlightService,
    public router: Router,
  ) {
    this.ws = new WebSocket("ws://localhost:3000");
    this.ws.onopen = () => {
      console.log("Web Socket opened");
    };
    this.ws.onmessage = () => {
      this.ngOnInit();
    }
  }

  public ngOnInit(): void {
    this.flightService.findAll().subscribe((flights) => {
      this.flights = flights;
    })
  }


  protected readonly console = console;
}

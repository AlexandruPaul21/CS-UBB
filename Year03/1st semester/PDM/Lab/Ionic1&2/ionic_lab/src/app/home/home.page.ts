import { Component, OnInit } from '@angular/core';
import { FlightService } from "../service/flight.service";
import { Flight } from "../model/flight";
import { Router } from "@angular/router";
import { Storage } from "@ionic/storage-angular";
import { Network } from "@capacitor/network";
import { Operation } from "../model/entities-with-operations";
import { AlertController } from "@ionic/angular";

interface MessageData {
  type: string;
  payload: Flight;
}

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  public queue: Operation[] = [];
  public displayedFlights: Flight[] = [];
  private allAvailableFlights: Flight[] = [];
  public networkStatus: boolean = true;
  public airplanes: string[] = [];

  constructor(
    private flightService: FlightService,
    public router: Router,
    public storage: Storage,
    private alertController: AlertController
  ) {
  }

  public async ngOnInit(): Promise<void> {
    Network.addListener('networkStatusChange', async status => {
      this.networkStatus = status.connected;
      if (this.networkStatus) {
        this.queue = await this.storage.get('operations-cache');

        for (const op of this.queue) {
          if (op.type!! == 'ADD') {
            console.log('ADD');
            (await this.flightService.save(op.entity!!)).subscribe();
          } else if (op.type!! == 'UPD') {
            (await this.flightService.update(op.entity!!._id!!, op.entity!!)).subscribe();
          } else if (op.type!! == 'DEL') {
            (await this.flightService.del(op.entity!!._id!!)).subscribe();
          }
        }
        this.queue = [];
        await this.storage.set('operations-cache', []);

        (await this.flightService.findAll()).subscribe((flights) => {
          this.displayedFlights = flights;
          this.allAvailableFlights = this.displayedFlights;
          this.airplanes = this.displayedFlights.map(value => value.plane!!);
        });

        const ws = new WebSocket('ws://localhost:3000');

        ws.onopen = async () => {
          const token = await this.storage.get('token');
          ws.send(JSON.stringify({ type: 'authorization', payload: { token } }));
        }

        ws.onmessage = async messageEvent => {
          (await this.flightService.findAll()).subscribe((flights) => {
            this.displayedFlights = flights;
            this.allAvailableFlights = this.displayedFlights;
            this.airplanes = this.displayedFlights.map(value => value.plane!!);
          });
        }
      }
    });

    this.queue = await this.storage.get('operations-cache');
    this.networkStatus = (await Network.getStatus()).connected;

    if (this.networkStatus) {
      (await this.flightService.findAll()).subscribe((flights) => {
        this.displayedFlights = flights;
        this.allAvailableFlights = this.displayedFlights;
        this.airplanes = this.displayedFlights.map(value => value.plane!!);
      });

      await this.storage.set('elements-cache', this.displayedFlights);
      const ws = new WebSocket('ws://localhost:3000');

      ws.onopen = async () => {
        const token = await this.storage.get('token');
        ws.send(JSON.stringify({ type: 'authorization', payload: { token } }));
      }

      ws.onmessage = async messageEvent => {
        (await this.flightService.findAll()).subscribe((flights) => {
          this.displayedFlights = flights;
          this.allAvailableFlights = this.displayedFlights;
        });
      }
    } else {
      this.displayedFlights = await this.storage.get('elements-cache');
      this.allAvailableFlights = this.displayedFlights;
      this.airplanes = this.displayedFlights.map(value => value.plane!!);
    }
  }

  public onIonInfinite($event: any) {
    setTimeout(() => {
      this.displayedFlights.push(...this.allAvailableFlights);
    }, 1000);
  }

  public onSelectChange($event: any) {
    const airplane = $event.detail.value;
    this.displayedFlights = this.allAvailableFlights.filter((value) => {
      return value.plane == airplane;
    })
  }

  public onSearchChange($event: any) {
    const searchTerm = $event.detail.value;
    this.displayedFlights = this.allAvailableFlights.filter((value) => {
      return value.destination?.includes(searchTerm);
    })
  }

  public async handleLogout() {
    await this.storage.remove('token');
  }
}

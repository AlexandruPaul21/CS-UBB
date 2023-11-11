import { Component, OnDestroy, OnInit } from '@angular/core';
import { Flight } from "../model/flight";
import { FlightService } from "../service/flight.service";
import { Subscription } from "rxjs";
import { Network } from "@capacitor/network";
import { Storage } from "@ionic/storage-angular";
import { Operation } from "../model/entities-with-operations";
import { AlertController } from "@ionic/angular";

@Component({
  selector: 'app-new-flight',
  templateUrl: './new-flight.component.html',
  styleUrls: ['./new-flight.component.scss'],
})
export class NewFlightComponent  implements OnInit, OnDestroy {
  public queue: Operation[] = [];
  public flight: Flight;
  private subscription: Subscription | null = null;
  public networkStatus: boolean = false;

  public constructor(private flightService: FlightService, private storage: Storage, private alertController: AlertController) {
    this.flight = new Flight();

    Network.addListener('networkStatusChange', status => {
      this.networkStatus = status.connected;
    });
  }

  public async ngOnInit(): Promise<void> {
    this.networkStatus = (await Network.getStatus()).connected;
  }

  public ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  public async saveFlight() {
    if (!this.networkStatus) {
      this.queue = await this.storage.get('operations-cache');
      this.queue[this.queue.length] = {type: 'ADD', entity: this.flight};
      await this.storage.set('operations-cache', this.queue);
    } else {
      this.subscription = (await this.flightService.save(this.flight)).subscribe(() => {});
    }
  }
}

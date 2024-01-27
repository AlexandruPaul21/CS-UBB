import { Component, OnDestroy, OnInit } from '@angular/core';
import { Flight } from "../model/flight";
import { ActivatedRoute } from "@angular/router";
import { FlightService } from "../service/flight.service";
import { Subscription } from "rxjs";
import { Network } from "@capacitor/network";
import { Operation } from "../model/entities-with-operations";
import { Storage } from "@ionic/storage-angular";

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.scss'],
})
export class ModifyComponent implements OnInit, OnDestroy {
  private queue: Operation[] = [];
  public flight: Flight;
  public date: Date = new Date();
  public isDeleteAction: boolean = false;
  private subscription: Subscription | null = null;
  private readonly id: string | null;
  public networkStatus: boolean = false;

  constructor(
    private activeRoute: ActivatedRoute,
    private flightService: FlightService,
    private storage: Storage
  ) {
    this.flight = {
      _id: 0,
      destination: '',
      plane: '',
      estimatedDeparture: new Date(),
      canceled: false,
      userId: "vI1GMkzBza5i82zj"
    };
    this.id = this.activeRoute.snapshot.paramMap.get('id');
    this.isDeleteAction = this.activeRoute.snapshot.paramMap.get('action') === 'delete';

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

  public ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  public async updateFlight() {
    if (!this.networkStatus) {
      this.queue = await this.storage.get('operations-cache');
      this.queue[this.queue.length] = {type: 'UPD', entity: this.flight};
      await this.storage.set('operations-cache', this.queue);
    } else {
      this.subscription = (await this.flightService.update(this.flight._id!!, this.flight)).subscribe(() => {
        //this.rxStompService.publish({ destination: "/topic/flights", body: "UPDATE"});
      });
    }

  }

  public async deleteFlight() {
    if (!this.networkStatus) {
      this.queue = await this.storage.get('operations-cache');
      this.queue[this.queue.length] = {type: 'DEL', entity: this.flight};
      await this.storage.set('operations-cache', this.queue);
    }
    else {
      this.subscription = (await this.flightService.del(this.flight._id!!)).subscribe(() => {
        //this.rxStompService.publish({ destination: "/topic/flights", body: "DELETE"});
      });
    }
  }
}

import { Component, OnDestroy } from '@angular/core';
import { Flight } from "../model/flight";
import { FlightService } from "../service/flight.service";
import { Subscription } from "rxjs";
import { PhotoService } from "../service/photo.service";
import { camera } from "ionicons/icons";

@Component({
  selector: 'app-new-flight',
  templateUrl: './new-flight.component.html',
  styleUrls: ['./new-flight.component.scss'],
})
export class NewFlightComponent  implements OnDestroy {
  public flight: Flight;
  private subscription: Subscription | null = null;

  public constructor(private flightService: FlightService, private photoService: PhotoService) {
    this.flight = new Flight();
  }

  public ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  public saveFlight() {
    this.subscription = this.flightService.save(this.flight).subscribe(() => {
    });
  }

  public async takePhoto() {
    const photo = await this.photoService.takeNewPhoto();
  }

  public async getPhoto() {
    const photo = await this.photoService.getNewPhoto();
  }

  protected readonly camera = camera;
}

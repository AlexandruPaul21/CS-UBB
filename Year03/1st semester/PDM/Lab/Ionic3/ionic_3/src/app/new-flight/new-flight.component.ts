import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Flight } from "../model/flight";
import { FlightService } from "../service/flight.service";
import { Subscription } from "rxjs";
import { PhotoService, UserPhoto } from "../service/photo.service";
import { camera } from "ionicons/icons";
import { GoogleMap } from "@capacitor/google-maps";

@Component({
  selector: 'app-new-flight',
  templateUrl: './new-flight.component.html',
  styleUrls: ['./new-flight.component.scss'],
})
export class NewFlightComponent  implements AfterViewInit ,OnInit, OnDestroy {
  @ViewChild('mapSelect')
  mapRef: ElementRef<HTMLElement>;
  newMap: GoogleMap;

  public flight: Flight;
  public photo: UserPhoto | null = null;
  private subscription: Subscription | null = null;
  private markerId: string = '';

  public constructor(private flightService: FlightService, private photoService: PhotoService) {
    this.flight = new Flight();
    console.log(this.flight);
  }

  public async ngAfterViewInit(): Promise<void> {
    this.newMap = await GoogleMap.create({
      id: 'my-map',
      element: this.mapRef.nativeElement,
      apiKey: 'your key here',
      config: {
        center: {
          lat: this.flight.latitude ?? 0,
          lng: this.flight.longitude ?? 0,
        },
        zoom: 8,
      }
    });

    await this.newMap.setOnMapClickListener(async (props) => {
      this.flight.latitude= props.latitude;
      this.flight.longitude = props.longitude;
      const lat = props.latitude;
      const lng = props.longitude;
      if (this.markerId !== '') {
        await this.newMap.removeMarker(this.markerId);
      }
      this.markerId = await this.newMap.addMarker({ coordinate: {lat, lng}});
    });
  }

  public async ngOnInit() {
    await this.photoService.loadPhotos();
  }

  public ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  public saveFlight() {
    this.flight.photoName = this.photo?.filepath ?? '';
    this.subscription = this.flightService.save(this.flight).subscribe(() => {
    });
  }

  public async takePhoto() {
    this.photo = await this.photoService.takeNewPhoto();
  }

  public async getPhoto() {
    this.photo = await this.photoService.getNewPhoto();
  }

  protected readonly camera = camera;
}

import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Flight } from "../model/flight";
import { ActivatedRoute } from "@angular/router";
import { FlightService } from "../service/flight.service";
import { Subscription } from "rxjs";
import { PhotoService, UserPhoto } from "../service/photo.service";
import { GoogleMap } from "@capacitor/google-maps";

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.scss'],
})
export class ModifyComponent implements OnInit, OnDestroy {
  @ViewChild('mapModify')
  mapRef: ElementRef<HTMLElement>;

  public flight: Flight;
  public date: Date = new Date();
  public isDeleteAction: boolean = false;
  private subscription: Subscription | null = null;
  public photo: UserPhoto;
  private photos: UserPhoto[] = [];
  private readonly id: string | null;
  private newMap: GoogleMap;
  private markerId: string;

  constructor(
    private activeRoute: ActivatedRoute,
    private flightService: FlightService,
    private photoService: PhotoService
  ) {
    this.flight = {
      id: 0,
      destination: '',
      plane: '',
      estimatedDeparture: new Date(),
      canceled: false,
      photoName: '',
      latitude: 0,
      longitude: 0
    };
    this.photo = {
      filepath: '',
      webviewPath: ''
    }
    this.id = this.activeRoute.snapshot.paramMap.get('id');
    this.isDeleteAction = this.activeRoute.snapshot.paramMap.get('action') === 'delete';
  }

  public async ngOnInit() {
    if (this.id == null) return;
    const id = Number(this.id);
    await this.photoService.loadPhotos();
    this.photos = this.photoService.photos;
    const photos = this.photos;
    this.flightService.findAll().subscribe(async flights => {
      this.flight = flights.filter((flight) => flight.id == id)[0] ?? null;
      photos.filter(photo => photo.filepath == this.flight.photoName);
      if (photos.length > 1) {
        this.photo = photos[0];
      }

      const lat = this.flight?.latitude ?? 0;
      const lng = this.flight?.longitude ?? 0;
      this.newMap = await GoogleMap.create({
        id: 'my-map',
        element: this.mapRef.nativeElement,
        apiKey: 'your key here',
        config: {
          center: { lat, lng },
          zoom: 8,
        }
      });
      this.markerId = await this.newMap.addMarker({ coordinate: {lat, lng}});

      if (!this.isDeleteAction) {
        await this.newMap.setOnMapClickListener(async (props) => {
          this.flight.latitude = props.latitude;
          this.flight.longitude = props.longitude;
          const lat = props.latitude;
          const lng = props.longitude;
          await this.newMap.removeMarker(this.markerId);
          this.markerId = await this.newMap.addMarker({ coordinate: { lat, lng } });
        });
      }
    });
  }


  public ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  public updateFlight() {
    if (this.photo.filepath !== '') {
      this.flight.photoName = this.photo.filepath;
    }
    this.subscription = this.flightService.update(this.flight.id!!, this.flight).subscribe(() => {
    });
  }

  public deleteFlight() {
    this.subscription = this.flightService.del(this.flight.id!!).subscribe(() => {
    });
  }

  public async takePhoto() {
    this.photo = await this.photoService.takeNewPhoto();
  }

  public async getPhoto() {
    this.photo = await this.photoService.getNewPhoto();
  }
}

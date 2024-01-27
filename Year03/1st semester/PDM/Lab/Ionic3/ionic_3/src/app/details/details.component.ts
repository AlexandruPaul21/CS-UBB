import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { Flight } from "../model/flight";
import { FlightService } from "../service/flight.service";
import { GoogleMap } from "@capacitor/google-maps";
import { Animation, AnimationController, IonCard } from "@ionic/angular";

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss'],
})
export class DetailsComponent implements AfterViewInit, OnInit {
  @ViewChild('map')
  mapRef: ElementRef<HTMLElement>;
  newMap: GoogleMap;

  public animation: Animation;

  @ViewChild(IonCard, { read: ElementRef }) card: ElementRef<HTMLIonCardElement>;

  public flight: Flight | null = null;
  private readonly id: string | null;

  constructor(
    private activeRoute: ActivatedRoute,
    private flightService: FlightService,
    private animationController: AnimationController,
    ) {
    this.id = this.activeRoute.snapshot.paramMap.get('id');
  }

  public ngAfterViewInit() {
    this.animation = this.animationController
      .create()
      .addElement(this.card.nativeElement)
      .duration(1500)
      .iterations(Infinity)
      .direction('alternate')
      .fromTo('background', 'blue', 'red');
  }

  public async ngOnInit() {
      if (this.id == null) return;
      const id = Number(this.id);
      this.flightService.findAll().subscribe(async (flights) => {
        this.flight = flights.filter((flight) => flight.id == id)[0] ?? null;

        const lat = this.flight?.latitude ?? 0;
        const lng = this.flight?.longitude ?? 0;
        this.newMap = await GoogleMap.create({
          id: 'my-map',
          element: this.mapRef.nativeElement,
          apiKey: 'AIzaSyCdUoBAAWexRUuH2rorUAFqYjQhUTlir80',
          config: {
            center: { lat, lng },
            zoom: 8,
          }
        });

        await this.newMap.addMarker({coordinate: {lat, lng}});
      });
  }

  public async overCardCallback() {
    await this.animation.play();
  }
}

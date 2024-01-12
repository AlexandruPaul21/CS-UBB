import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FlightService } from "../service/flight.service";
import { Flight } from "../model/flight";
import { Router } from "@angular/router";
import { PhotoService } from "../service/photo.service";
import { AnimationController, Animation, IonButton, IonModal } from "@ionic/angular";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit, AfterViewInit {
  @ViewChild(IonButton, {read: ElementRef}) button: ElementRef<HTMLIonButtonElement> | undefined;
  @ViewChild('modal', { static: true }) modal: IonModal;
  public animation: Animation | undefined;
  public flights: Flight[] = [];
  private ws: WebSocket;

  constructor(
    private flightService: FlightService,
    public router: Router,
    public photoService: PhotoService,
    private animationController: AnimationController
  ) {
    this.ws = new WebSocket("ws://localhost:3000");
    this.ws.onopen = () => {
      console.log("Web Socket opened");
    };
    this.ws.onmessage = () => {
      this.loadFlights();
    }
  }

  public ngAfterViewInit(): void {
    this.animation = this.animationController
      .create()
      .addElement(this.button!!.nativeElement)
      .duration(1500)
      .iterations(Infinity)
      .fromTo('opacity', '1', '0.5')
      .fromTo('width', '100px', '300px');


    const enterAnimation = (baseEl: HTMLElement) => {
      const root = baseEl.shadowRoot;

      const backdropAnimation = this.animationController
        .create()
        .addElement(root!.querySelector('ion-backdrop')!)
        .fromTo('opacity', '0.01', 'var(--backdrop-opacity)');

      const wrapperAnimation = this.animationController
        .create()
        .addElement(root!.querySelector('.modal-wrapper')!)
        .keyframes([
          { offset: 0, opacity: '0', transform: 'scale(0)' },
          { offset: 1, opacity: '0.99', transform: 'scale(1)' },
        ]);

      return this.animationController
        .create()
        .addElement(baseEl)
        .easing('ease-out')
        .duration(500)
        .addAnimation([backdropAnimation, wrapperAnimation]);
    };

    const leaveAnimation = (baseEl: HTMLElement) => {
      return enterAnimation(baseEl).direction('reverse');
    };

    this.modal.enterAnimation = enterAnimation;
    this.modal.leaveAnimation = leaveAnimation;
  }

  private loadFlights() {
    this.flightService.findAll().subscribe((flights) => {
      this.flights = flights;
    });
  }

  public async ngOnInit() {
    this.flightService.findAll().subscribe((flights) => {
      this.flights = flights;
    });
    await this.photoService.loadPhotos();
  }

  closeModal() {
    this.modal.dismiss();
  }

  public getPhotoById(id: number) {
    const flight = this.flights.filter(flight => flight.id == id)[0];
    const photo = this.photoService.photos.filter(photo => photo.filepath == flight.photoName);
    if (photo.length >= 1) {
      return photo[0].webviewPath;
    }

    return null;
  }
}

import { Component, OnInit } from '@angular/core';
import { camera } from "ionicons/icons";
import { PhotoService } from "../service/photo.service";

@Component({
  selector: 'app-photo',
  templateUrl: './photo.component.html',
  styleUrls: ['./photo.component.scss'],
})
export class PhotoComponent  implements OnInit {

  constructor(private photoService: PhotoService) { }

  ngOnInit() {}

    protected readonly camera = camera;

  async takePhoto() {
    await this.photoService.takeNewPhoto();
  }
}

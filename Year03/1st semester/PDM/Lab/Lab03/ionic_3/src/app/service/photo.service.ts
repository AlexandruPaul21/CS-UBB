import { Injectable } from '@angular/core';
import { Camera, CameraResultType, CameraSource, Photo } from "@capacitor/camera";
import { Directory, Filesystem } from "@capacitor/filesystem";
import { Preferences } from "@capacitor/preferences";

@Injectable({
  providedIn: 'root'
})
export class PhotoService {
  public photos: UserPhoto[] = [];
  private PHOTOS_KEY = 'photos';

  constructor() {
  }

  public async loadPhotos() {
    const savedPhotosPref = await Preferences.get({ key: this.PHOTOS_KEY }).then(result => result.value);
    if (savedPhotosPref == null) return;
    const savedPhotos = (savedPhotosPref ? JSON.parse(savedPhotosPref) : []) as UserPhoto[];

    for (let photo of savedPhotos) {
      const data = await this.readPicture(photo.filepath);
      photo.webviewPath = `data:image/jpeg;base64,${data}`;
    }
    this.photos = savedPhotos;
  }

  public async takeNewPhoto() {
    const capturedPhoto = await Camera.getPhoto({
      resultType: CameraResultType.Base64,
      source: CameraSource.Camera,
      quality: 100,
    });

    const photo = await this.savePicture(capturedPhoto) as UserPhoto;

    this.photos.unshift(photo);

    await Preferences.set({ key: this.PHOTOS_KEY, value: JSON.stringify(this.photos) });

    return photo;
  }

  public async getNewPhoto() {
    const capturedPhoto = await Camera.getPhoto({
      resultType: CameraResultType.Base64,
      source: CameraSource.Photos,
      quality: 100
    });

    const photo = await this.savePicture(capturedPhoto) as UserPhoto;

    this.photos.unshift(photo);

    await Preferences.set({ key: this.PHOTOS_KEY, value: JSON.stringify(this.photos) });

    return photo;
  }



  private async readPicture(path: string) {
    return Filesystem.readFile({
      path,
      directory: Directory.Data
    }).then(result => {
        return result.data;
      }
    )
  }

  private async savePicture(photo: Photo) {
    const base64Data = photo.base64String;

    const fileName = Date.now() + '.jpeg';
    await Filesystem.writeFile({
      path: fileName,
      data: base64Data!!,
      directory: Directory.Data
    });

    const webviewPath = `data:image/jpeg;base64,${ base64Data }`

    return {
      filepath: fileName,
      webviewPath: webviewPath
    };
  }

  private async readAsBase64(photo: Photo) {
    const response = await fetch(photo.webPath!);
    const blob = await response.blob();

    return await this.convertBlobToBase64(blob) as string;
  }

  private convertBlobToBase64 = (blob: Blob) => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onerror = reject;
    reader.onload = () => {
      resolve(reader.result);
    };
    reader.readAsDataURL(blob);
  });
}

export interface UserPhoto {
  filepath: string;
  webviewPath?: string;
}

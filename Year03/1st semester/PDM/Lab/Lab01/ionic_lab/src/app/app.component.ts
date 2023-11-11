import { Component, OnInit } from '@angular/core';
import { Storage } from "@ionic/storage-angular";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit{
  constructor(private storage: Storage) {
  }
  public async ngOnInit() {
    await this.storage.create();
    await this.storage.set('operations-cache', []);
    await this.storage.set('elements-cache', []);
  }
}

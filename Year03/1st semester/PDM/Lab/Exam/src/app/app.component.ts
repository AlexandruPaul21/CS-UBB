import { Component, OnInit } from '@angular/core';
import { Storage } from "@ionic/storage-angular";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit {
  public constructor(
    private storage: Storage,
  ) {}

  public async ngOnInit(): Promise<void> {
    await this.storage.create();
  }
}

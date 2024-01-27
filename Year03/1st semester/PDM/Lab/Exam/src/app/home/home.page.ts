import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { EntityService } from "../service/entity.service";
import { Storage } from "@ionic/storage-angular";
import { Product } from "../domain/Product";
import { ServerResponse } from "../domain/Response";
import { DisplayedItem } from "../domain/DisplayedItem";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  public page: number = 1;
  public totalPages: number = 0;
  public products: Product[] = [];
  public downloading: boolean = true;
  public selectedProducts: Product[] = [];
  public selectedProduct: Product = new Product();
  public cart: DisplayedItem[] = [];
  public quantity: number = 0;
  public uploadRunning: boolean = false;

  private ws: WebSocket;

  public reloadReceived: boolean = false;

  constructor(
    public router: Router,
    private service: EntityService,
    private storage: Storage,
  ) {
    this.ws = new WebSocket("ws://localhost:3000");
    this.ws.onopen = () => {
      console.log("WebSocket open");
    }

    this.ws.onmessage = () => {
      console.log("On message");
      this.reloadReceived = true;
    }
  }

  public async ngOnInit(): Promise<void> {
    this.cart = (await this.storage.get('cart')) ?? [];
    this.products = (await this.storage.get('products')) ?? [];

    if (this.products.length == 0) {
      const response: ServerResponse = await this.service.getPage(this.page);
      this.totalPages = (response.total ?? 0) / 10;
      await this.storage.set('total', this.totalPages);
      this.products.push(...response.products);

      this.page += 1;
    } else {
      this.page = this.products.length / 10;
      this.totalPages = await this.storage.get('total');
    }

    while (this.page <= this.totalPages) {
      try {
        const response = await this.service.getPage(this.page);
        this.products.push(...response.products);
        await this.storage.set('products', this.products);
        this.page += 1;
      } catch (e) {
        this.downloading = false;
        break;
      }
    }
  }

  public async onDownloadClick() {
    if (this.reloadReceived) {
      this.page = 1;
      this.products = [];
      await this.storage.set('products', this.products);
      await this.ngOnInit();
      this.reloadReceived = false;
    }

    this.downloading = true;
    while (this.page <= this.totalPages) {
      try {
        const response = await this.service.getPage(this.page);
        this.products.push(...response.products);
        this.page += 1;
      } catch (e) {
        this.downloading = false;
        break;
      }
    }
  }

  public onInputChange(value: string | null | undefined) {
    if (value != null) {
      if (value === '') {
        this.selectedProducts = [];
        return;
      }
      this.selectedProducts = this.products.filter((product) => product.name?.includes(value)).slice(0, 5);
    }
  }

  public async onUploadClicked() {
    if (this.uploadRunning) {
      return;
    }
    this.uploadRunning = true;

    for (let i = 0; i < this.cart.length; i++) {
      if (this.notYetUploaded(this.cart[i])) {
        this.cart[i].submitted = 1;
        try {
          await this.service.sendItem(this.cart[i].item!!);
          this.cart[i].submitted = 2;
        } catch (e) {
          this.cart[i].submitted = 9;
          this.uploadRunning = false;
          return;
        } finally {
          await this.storage.set('cart', this.cart);
        }
      }
    }

    this.uploadRunning = false;
  }

  public async onAddPushed() {
    this.cart.push(
      {
        name: this.selectedProduct.name,
        item: {
          code: this.selectedProduct.code,
          quantity: this.quantity
        },
        submitted: 0
      }
    );
    await this.storage.set('cart', this.cart);
  }

  public getStatusText(submitted: number): string {
    switch (submitted) {
      case 0:
        return 'Newly added';
      case 1:
        return 'Sending...';
      case 2:
        return 'Sent';
      case 9:
        return 'Failed';
      default:
        return '';
    }
  }

  public notYetUploaded(item: DisplayedItem): boolean {
    return item.submitted === 0 || item.submitted === 9;
  }

  public hasUnsentProducts(cart: DisplayedItem[]): boolean {
    return cart.find(item => this.notYetUploaded(item)) !== undefined;
  }
}

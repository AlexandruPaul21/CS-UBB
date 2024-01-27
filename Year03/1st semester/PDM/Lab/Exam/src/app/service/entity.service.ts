import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Storage } from "@ionic/storage-angular";
import { catchError, lastValueFrom, take } from "rxjs";
import { Item } from "../domain/Item";

@Injectable({
  providedIn: 'root'
})
export class EntityService {
  private readonly serverUrl: string;

  constructor(
    private storage: Storage,
    private http: HttpClient
  ) {
    this.serverUrl = "http://localhost:3000";
  }

  public sendItem(item: Item): Promise<any> {
    return lastValueFrom(
      this.http.post(this.serverUrl + '/item', item)
        .pipe(
          take(1)
        )
    );
  }

  public getPage(pageNo: number): Promise<any> {
    return lastValueFrom(
      this.http
        .get(this.serverUrl + '/product?page=' + pageNo)
        .pipe(
          take(1),
          catchError(this.handleError)
        ));
  }

  private handleError(error: HttpErrorResponse) {
    return "X";
  }

  private async getTokenHeaders() {
    const token = await this.storage.get('token');
    return new HttpHeaders().set("Authorization", token);
  }

}

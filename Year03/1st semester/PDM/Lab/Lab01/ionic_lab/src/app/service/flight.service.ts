import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Flight } from "../model/flight";
import { Observable } from "rxjs";
import { Storage } from "@ionic/storage-angular";

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  private readonly serverUrl: string;

  constructor(private http: HttpClient, private storage: Storage) {
    this.serverUrl = "http://localhost:3000/api/item";
  }

  public async findAll(): Promise<Observable<Flight[]>> {
    const token = await this.storage.get('token');
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.get<Flight[]>(this.serverUrl, { headers: headers });
  }

  public async findOne(id: number): Promise<Observable<Flight>> {
    const token = await this.storage.get('token');
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.get<Flight>(this.serverUrl + '/' + id, { headers: headers });
  }

  public async save(flight: Flight) {
    const token = await this.storage.get('token');
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.post(this.serverUrl, flight, { headers: headers });
  }

  public async update(id: number, flight: Flight) {
    const token = await this.storage.get('token');
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.put(this.serverUrl + '/' + id, flight, { headers: headers });
  }

  public async del(id: number) {
    const token = await this.storage.get('token');
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.delete(this.serverUrl + '/' + id, { headers: headers });
  }

}

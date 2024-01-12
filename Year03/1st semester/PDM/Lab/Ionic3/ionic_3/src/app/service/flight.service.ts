import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Flight } from "../model/flight";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  private readonly serverUrl: string;

  constructor(private http: HttpClient) {
    this.serverUrl = "http://localhost:3000/item";
  }

  public findAll() : Observable<Flight[]> {
    return this.http.get<Flight[]>(this.serverUrl);
  }

  public findOne(id: number): Observable<Flight> {
    return this.http.get<Flight>(this.serverUrl + '/' + id);
  }

  public save(flight: Flight) {
    return this.http.post(this.serverUrl, flight);
  }

  public update(id: number, flight: Flight) {
    return this.http.put(this.serverUrl + '/' + id, flight);
  }

  public del(id: number) {
    return this.http.delete(this.serverUrl + '/' + id);
  }

}

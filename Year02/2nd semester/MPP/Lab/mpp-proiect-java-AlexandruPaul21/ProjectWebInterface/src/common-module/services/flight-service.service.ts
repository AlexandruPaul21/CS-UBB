import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Flight} from "../model/Flight";

@Injectable({
  providedIn: 'root'
})
export class FlightServiceService {

  private url: string;

  constructor(
    private http: HttpClient
  ) {
    this.url = "http://localhost:8080/api/flight";
  }

  public findAll(): Observable<Flight[]> {
    return this.http.get<Flight[]>(this.url);
  }

  public delete(id: number): Observable<Flight> {
    return this.http.delete<Flight>(this.url + "/" + id);
  }

  public addFlight(flight: Flight) {
    return this.http.post<Flight>(this.url, flight);
  }

  public findOne(id: string) {
    return this.http.get<Flight>(this.url + "/" + id);
  }

  public updateFlight(flight: Flight) {
    return this.http.put<Flight>(this.url + "/" + flight.id, flight);
  }
}

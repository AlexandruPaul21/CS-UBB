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
    this.serverUrl = "http://localhost:8080/api/flights";
  }

  public findAll() : Observable<Flight[]> {
    return this.http.get<Flight[]>(this.serverUrl);
  }

  public findOne(id: number): Observable<Flight> {
    return this.http.get<Flight>(this.serverUrl + '/' + id);
  }

}

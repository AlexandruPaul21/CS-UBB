import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private readonly serverUrl: string;

  constructor(private http: HttpClient) {
    this.serverUrl = "http://localhost:3000/api/auth";
  }

  public login(user: any): Observable<any> {
    return this.http.post(this.serverUrl + '/login', user);
  }
}

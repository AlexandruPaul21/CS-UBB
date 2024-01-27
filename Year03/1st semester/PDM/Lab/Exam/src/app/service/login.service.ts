import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { catchError, Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private readonly serverUrl: string;

  constructor(private http: HttpClient) {
    this.serverUrl = "http://localhost:3000/auth";
  }

  public login(data: string): Observable<unknown> {
    // TODO
    return this.http.post(this.serverUrl, { data }).pipe(
      catchError(this.handleError)
    );
  }

  // TODO
  private handleError(error: HttpErrorResponse) {
    return "X";
  }
}

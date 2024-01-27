import { Component, OnInit } from '@angular/core';
import { LoginService } from "../service/login.service";
import { Router } from "@angular/router";
import { Storage } from "@ionic/storage-angular";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  public data: string = '';
  public label: string = '';

  constructor(
    public loginService: LoginService,
    private storage: Storage,
    private router: Router,
  ) { }

  public async ngOnInit() {
    const token = await this.storage.get('token');
    if (token != null) {
      await this.router.navigate(['items']);
    }
  }

  public async loginClicked() {
    this.loginService.login(this.data).subscribe((token) => {
      if (token === 'X') {
        this.label = "Error. Try again";
      } else {
        // @ts-ignore
        this.storage.set('token', token.token);
        this.router.navigate(['items']);
      }
    });
  }

}

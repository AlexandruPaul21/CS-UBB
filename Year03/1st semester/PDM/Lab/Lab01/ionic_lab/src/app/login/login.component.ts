import { Component, OnInit } from '@angular/core';
import { LoginService } from "../service/login.service";
import { Storage } from "@ionic/storage-angular";
import { Router } from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent  implements OnInit {

  public user = {username: '', password: ''};

  public constructor(public loginService: LoginService, private storage: Storage, private router: Router) {

  }

  public async ngOnInit() {
    const token = await this.storage.get('token');
    if (token != null) {
      await this.router.navigate(['/items']);
    }
  }

  public async loginClicked() {
    this.loginService.login(this.user).subscribe(async (data: any) => {
      await this.storage.set('token', data.token);
      await this.router.navigate(['/items']);
    });
  }
}

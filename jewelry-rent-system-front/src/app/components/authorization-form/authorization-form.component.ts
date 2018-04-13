import { Component, OnInit, Injectable } from '@angular/core';
import { toast } from 'angular2-materialize';

import { Credential } from '../../models/credential';
import { AuthorizationService } from '../../services/auth-service';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'authorization-form',
  templateUrl: './authorization-form.component.html',
  styleUrls: ['./authorization-form.component.css']
})
@Injectable()
export class AuthorizationFormComponent implements OnInit {

  loginData: Credential = new Credential();

  constructor(private service: AuthorizationService, public currentUser: UserService) { }

  ngOnInit() {
  }

  signIn() {
    this.service.login(this.loginData).then(() => {
      this.loginData = new Credential();
    }).catch(() => {
      toast("Authentification failed", 4000);
    });
  }

  signOut(){
    this.service.logout();
  }
}

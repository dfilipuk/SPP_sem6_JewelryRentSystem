import { Component, OnInit } from '@angular/core';

import { Credential } from '../../models/credential';
import { IAuthorizationService } from '../../services/interfaces/auth-service-interface';
import { AuthorizationService } from '../../services/auth-service';

@Component({
  selector: 'authorization-form',
  templateUrl: './authorization-form.component.html',
  styleUrls: ['./authorization-form.component.css'],
  providers: [AuthorizationService]
})
export class AuthorizationFormComponent implements OnInit {

  loginData: Credential = new Credential();

  constructor(private service: AuthorizationService) { }

  ngOnInit() {
  }

  signIn() {
    this.service.login(this.loginData).subscribe(data => {
      console.log(data);
    });
  }

  signOut(){
    this.service.logout();
  }
}

import { Component, OnInit } from '@angular/core';
import {LoginRequest} from '../../models/login/login-request';

@Component({
  selector: 'authorization-form',
  templateUrl: './authorization-form.component.html',
  styleUrls: ['./authorization-form.component.css']
})
export class AuthorizationFormComponent implements OnInit {

  loginData: LoginRequest = new LoginRequest();

  constructor() { }

  ngOnInit() {
  }

  signIn(){
    console.log(this.loginData);
  }
}

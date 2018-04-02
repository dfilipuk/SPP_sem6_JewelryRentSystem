import { Component, OnInit } from '@angular/core';
import { Credential } from '../../models/credential';

@Component({
  selector: 'authorization-form',
  templateUrl: './authorization-form.component.html',
  styleUrls: ['./authorization-form.component.css']
})
export class AuthorizationFormComponent implements OnInit {

  loginData: Credential = new Credential();

  constructor() { }

  ngOnInit() {
  }

  signIn() {
    console.log(this.loginData);
  }
}

import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-entities-page',
  templateUrl: './entities-page.component.html',
  styleUrls: ['./entities-page.component.css']
})
export class EntitiesPageComponent implements OnInit {

  constructor(public currentUser: UserService) { }

  ngOnInit() {
  }

}

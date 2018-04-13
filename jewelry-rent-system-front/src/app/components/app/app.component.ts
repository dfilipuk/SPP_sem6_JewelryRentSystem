import { Component, Injectable } from '@angular/core';
import { AuthorizationService } from '../../services/auth-service';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
@Injectable()
export class AppComponent {

  constructor(private authService: AuthorizationService, private currentUser: UserService) {
  }

  logout(){
    this.authService.logout();
  }
}

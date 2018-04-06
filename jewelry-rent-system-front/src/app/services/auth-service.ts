import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import { IAuthorizationService } from './interfaces/auth-service-interface';
import { NetworkService } from './base/network-service-base';
import { EnvironmentService } from './common/enviroment-service';
import { Credential } from '../models/credential';
import { UserService } from './user-service';

@Injectable()
export class AuthorizationService extends NetworkService implements IAuthorizationService {

    private controllerName: string = "/auth";

    constructor(http: Http, private userService: UserService) {
        super(EnvironmentService.ServerUrl, EnvironmentService.AuthTokenName, http);
    }

    login(credential: Credential): Promise<void> {
        let request = this.post(this.controllerName + "/login", credential);
        let self = this;
        return request.toPromise().then((data: any) => {
            self.storage.set(this.authTokenName, data.token);
            this.userService.updateCurrentUser();            
        });
    }

    logout(): Promise<void> {
        return new Promise(() => {
            this.storage.remove(this.authTokenName);
            this.userService.updateCurrentUser();
        });
    }
}
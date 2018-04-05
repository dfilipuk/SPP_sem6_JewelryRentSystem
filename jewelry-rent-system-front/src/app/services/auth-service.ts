import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';

import { IAuthorizationService } from './interfaces/auth-service-interface';
import { NetworkService } from './base/network-service-base';
import { EnvironmentService } from './common/enviroment-service';
import { Credential } from '../models/credential';
import { Scheduler } from 'rxjs/Scheduler';

@Injectable()
export class AuthorizationService extends NetworkService implements IAuthorizationService {

    private controllerName: string = "/auth";

    constructor(http: Http) {
        super(EnvironmentService.ServerUrl, EnvironmentService.AuthTokenName, http);
    }

    login(credential: Credential): Observable<Object> {
        let request = this.post(this.controllerName + "/login", credential);
        let self =  this;
        request.subscribe((data: any) => {
            self.storage.set(this.authTokenName, data.token);
            this.getCurrentUser();
        });
        return request;
    }

    logout(): Observable<Object> {
        this.storage.remove(this.authTokenName);
        return new Observable();
    }

    private getCurrentUser() {
        this.get(this.controllerName + "/user").subscribe(x => console.log(x));
    }
}
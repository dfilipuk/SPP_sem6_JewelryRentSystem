import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';

import { IAuthorizationService } from './interfaces/auth-service-interface';
import { IStorageService } from './interfaces/storage-service-interface';
import { NetworkService } from './base/network-service-base';
import { EnvironmentService } from './common/enviroment-service';
import { Credential } from '../models/credential';

@Injectable()
export class AuthorizationService extends NetworkService implements IAuthorizationService {

    private controllerName: string = "/auth";

    constructor(http: HttpClient, storage: IStorageService) {
        super(EnvironmentService.ServerUrl, EnvironmentService.AuthTokenName, http, storage);
    }

    login(credential: Credential): Observable<Object> {
        return this.post(this.controllerName + "/login", credential);
    }

    logout(): Observable<Object> {
        throw new Error("Method not implemented.");
    }
}
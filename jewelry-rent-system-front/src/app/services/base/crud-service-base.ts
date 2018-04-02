import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';

import { NetworkService } from './network-service-base';
import { EnvironmentService } from '../common/enviroment-service';
import { ICrudService } from '../interfaces/crud-service-interface';
import { IStorageService } from '../interfaces/storage-service-interface';

@Injectable()
export abstract class CrudService<T> extends NetworkService implements ICrudService<T> {

    constructor(private controllerName: string, http: HttpClient, storage: IStorageService) {
        super(EnvironmentService.ServerUrl, EnvironmentService.AuthTokenName, http, storage);
    }

    getAll(): Observable<Object> {
        return this.get(this.controllerName + '/list');
    }

    create(item: T): Observable<Object> {
        return this.post(this.controllerName + '/create', item);
    }

    update(item: T): Observable<Object> {
        return this.post(this.controllerName + '/update', item);
    }

    delete(id: number): Observable<Object> {
        return this.post(this.controllerName + '/delete', id);
    }
}
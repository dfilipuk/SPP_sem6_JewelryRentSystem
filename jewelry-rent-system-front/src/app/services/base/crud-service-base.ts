import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';

import { NetworkService } from './network-service-base';
import { EnvironmentService } from '../common/enviroment-service';
import { ICrudService } from '../interfaces/crud-service-interface';

export abstract class CrudService<T> extends NetworkService implements ICrudService<T> {

    constructor(private controllerName: string, http: Http) {
        super(EnvironmentService.ServerUrl, EnvironmentService.AuthTokenName, http);
        this.controllerName = '/' + controllerName;
    }

    getAll(): Observable<T[]> {
        return this.get(this.controllerName + '/list');
    }

    create(item: T): Observable<number> {
        return this.post(this.controllerName + '/create', item);
    }

    update(item: T): Observable<void> {
        return this.post(this.controllerName + '/update', item);
    }

    delete(id: number): Observable<void> {
        return this.post(this.controllerName + '/delete', { 'id': id });
    }
}
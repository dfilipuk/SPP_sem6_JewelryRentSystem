import { ICrudService } from './interfaces/crud-service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export abstract class BaseService<T> implements ICrudService<T> {

    private baseUrl = "http://localhost:8080/";
    private controllerUrl: string;

    constructor(controllerName: string, private http: HttpClient) {
        this.controllerUrl = this.baseUrl + controllerName;
    }

    getAll(): Observable<Object> {
        let url = this.controllerUrl + '/list';
        return this.http.get(url);
    }

    create(item: T): Observable<Object> {
        let url = this.controllerUrl + '/create';
              debugger;
        return this.http.post(url, item);
    }

    update(item: T): Observable<Object> {
        let url = this.controllerUrl + '/update';
        return this.http.post(url, item);
    }

    delete(id: number): Observable<Object> {
        let url = this.controllerUrl + '/delete';
        return this.http.post(url, id);
    }
}
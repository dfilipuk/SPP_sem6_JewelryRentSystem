import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Headers } from '@angular/http';

import { IStorageService } from '../interfaces/storage-service-interface';
import { StorageService } from '../common/storage-service';

export abstract class NetworkService {

    protected storage: IStorageService

    constructor(
        protected serverUrl: string,
        protected authTokenName: string,
        protected http: Http) {
        this.storage = new StorageService();
    }

    protected get(location: string): Observable<any> {
        let url = this.serverUrl + location;
        let headers = this.getHeaders();
        return this.http.get(url, { headers: headers })
            .map(response => response.json());
    }

    protected post(location: string, data: any): Observable<any> {
        let url = this.serverUrl + location;
        let headers = this.getHeaders();
        return this.http.post(url, data, { headers: headers })
            .map(response => response.json());
    }

    private getHeaders() {
        let authToken = this.storage.get(this.authTokenName);
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('JrsAuthToken', authToken);
        return headers;
    }
}
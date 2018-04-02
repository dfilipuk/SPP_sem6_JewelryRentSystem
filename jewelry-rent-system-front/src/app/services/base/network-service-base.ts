import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { IStorageService } from '../interfaces/storage-service-interface';

@Injectable()
export abstract class NetworkService {

    constructor(
        private serverUrl: string,
        private authTokenName: string,
        private http: HttpClient,
        private storage: IStorageService) {
    }

    protected get(location: string) {
        let url = this.serverUrl + location;
        let headers = this.getHeaders();
        return this.http.get(url, { headers: headers });
    }

    protected post(location: string, data: any) {
        let url = this.serverUrl + location;
        let headers = this.getHeaders();
        return this.http.post(url, data, { headers: headers });
    }

    private getHeaders() {
        var authToken = this.storage.get(this.authTokenName);
        const headers = new HttpHeaders();
        headers.append('Authorization', authToken);
        return headers;
    }
}
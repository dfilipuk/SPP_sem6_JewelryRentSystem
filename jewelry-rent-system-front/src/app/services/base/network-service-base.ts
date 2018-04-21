import { Observable } from 'rxjs/Observable';
import { Http, Headers, Response } from '@angular/http';

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
        return this.http.get(url, { headers: headers }).map(this.getResponse);
    }

    protected post(location: string, data: any): Observable<any> {
        let url = this.serverUrl + location;
        let headers = this.getHeaders();
        return this.http.post(url, data, { headers: headers }).map(this.getResponse);
    }

    protected openWindow(location: string) {
        let url = this.serverUrl + location;
        window.open(url);
    }

    private getHeaders() {
        let authToken = this.storage.get(this.authTokenName);
        let headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('JrsAuthToken', authToken);
        return headers;
    }

    private getResponse(response: Response) {
        return response.status == 200 && response.arrayBuffer().byteLength == 0
            ? null
            : response.json();
    }
}
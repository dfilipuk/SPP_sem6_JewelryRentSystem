import { Observable } from 'rxjs/Observable';

import { Credential } from '../../models/credential';

export interface IAuthorizationService {
    login(credential: Credential): Observable<Object>;
    logout(): Observable<Object>;
}
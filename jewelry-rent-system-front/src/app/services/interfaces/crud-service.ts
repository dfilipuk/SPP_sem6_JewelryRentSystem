import {Observable} from 'rxjs/Observable';

export interface ICrudService<T>{
    getAll(): Observable<Object>;
    create(item: T): Observable<Object>;
    update(item: T): Observable<Object>;
    delete(id: number): Observable<Object>;
}
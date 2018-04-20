import {Observable} from 'rxjs/Observable';

export interface ICrudService<T>{
    getAll(): Observable<T[]>;
    create(item: T): Observable<number>;
    update(item: T): Observable<void>;
    delete(id: number): Observable<void>;
}
import { CrudService } from './base/crud-service-base';
import { ICrudService } from './interfaces/crud-service-interface';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { Jewelry } from '../models/jewelry';

@Injectable()
export class JewelryService extends CrudService<Jewelry> implements ICrudService<Jewelry> {
    constructor(http: Http) {
        super('jewelry', http);
    }
}
import { CrudService } from './base/crud-service-base';
import { ICrudService } from './interfaces/crud-service-interface';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { Order } from '../models/order';

@Injectable()
export class OrderService extends CrudService<Order> implements ICrudService<Order> {
    constructor(http: Http) {
        super('order', http);
    }
}
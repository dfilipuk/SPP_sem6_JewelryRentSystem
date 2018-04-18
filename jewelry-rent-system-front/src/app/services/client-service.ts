import { CrudService } from './base/crud-service-base';
import { ICrudService } from './interfaces/crud-service-interface';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { Client } from '../models/client';

@Injectable()
export class ClientService extends CrudService<Client> implements ICrudService<Client> {
    constructor(http: Http) {
        super('client', http);
    }
}
import { CrudService } from './base/crud-service-base';
import { ICrudService } from './interfaces/crud-service-interface';
import { Branch } from '../models/branch';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';

@Injectable()
export class BranchService extends CrudService<Branch> implements ICrudService<Branch> {
    constructor(http: Http) {
        super('branch', http);
    }
}
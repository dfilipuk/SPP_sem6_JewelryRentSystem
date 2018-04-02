import { CrudService } from './base/crud-service-base';
import { ICrudService } from './interfaces/crud-service-interface';
import { IStorageService } from './interfaces/storage-service-interface';
import { Branch } from '../models/branch';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class BranchService extends CrudService<Branch> {
    constructor(http: HttpClient, storage: IStorageService) {
        super('branch', http, storage);
    }
}
import { BaseService } from './base-service';
import { ICrudService } from './interfaces/crud-service';
import { Branch } from '../models/branch';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
 
@Injectable()
export class BranchService extends BaseService<Branch> {
    constructor(http: HttpClient) {
        super('branch', http);
    }
}
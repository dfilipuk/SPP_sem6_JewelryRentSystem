import { CrudService } from './base/crud-service-base';
import { ICrudService } from './interfaces/crud-service-interface';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { Employee } from '../models/employee';

@Injectable()
export class EmployeeService extends CrudService<Employee> implements ICrudService<Employee> {
    constructor(http: Http) {
        super('employee', http);
    }
}
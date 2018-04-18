import { CrudService } from './base/crud-service-base';
import { ICrudService } from './interfaces/crud-service-interface';
import { Http } from '@angular/http';
import { Injectable } from '@angular/core';
import { Material } from '../models/material';

@Injectable()
export class MaterialService extends CrudService<Material> implements ICrudService<Material> {
    constructor(http: Http) {
        super('material', http);
    }
}
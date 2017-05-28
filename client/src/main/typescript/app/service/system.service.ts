import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/map'

import { System } from "../../model/system.model";

@Injectable()
export class SystemService {
    constructor(private http: Http) {}

    get(name: string): Promise<System> {
        const self = this;
        return new Promise<System>(function(resolve, _) {
            self.http.get('api/v1/system/' + name).map(response => <System>response.json()).first().subscribe(system => resolve(system));
        });
    }
}



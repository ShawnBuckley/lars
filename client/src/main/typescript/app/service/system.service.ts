import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map'

import {System} from "../model/system.model";

@Injectable()
export class SystemService {
    constructor(private http: Http) {}

    get(name: string): Observable<System> {
        return this.http.get('rest/system/' + name).map(response => <System>response.json());
    }
}



import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/map'

import {System} from "../model/system.model";

@Injectable()
export class SystemService {
    constructor(private http: Http) {}

    get(name: string, callback: (system: System) => void): void {
        this.http.get('rest/system/' + name).map(response => <System>response.json()).first().subscribe(system => callback(system));
    }
}



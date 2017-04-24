import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map'

@Injectable()
export class GameService {
    constructor(private http: Http) {}

    isRunning(): Observable<boolean> {
        return this.http.get('rest/game/running').map(response => <boolean>response.json());
    }

    pause(): Observable<boolean> {
        return this.http.post('rest/game/pause', {}, {}).map(response => <boolean>response.json());
    }
}



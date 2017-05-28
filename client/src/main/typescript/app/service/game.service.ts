import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/map'

@Injectable()
export class GameService {
    constructor(private http: Http) {}

    isRunning(): Promise<boolean> {
        const self = this;
        return new Promise<boolean>(function(resolve, _) {
            self.http.get('api/v1/game/running').map(response => <boolean>response.json()).first().subscribe(result => resolve(result));
        });
    }

    pause(): Promise<boolean> {
        const self = this;
        return new Promise<boolean>(function(resolve, _) {
            self.http.post('api/v1/game/pause', {}, {}).map(response => <boolean>response.json()).first().subscribe(result => resolve(result));
        });

    }
}



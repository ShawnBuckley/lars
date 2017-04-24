import { Component, OnInit, ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/switchMap';

import { SystemService } from "../../service/system.service";
import { System } from "../../model/system.model";
import { SystemViewComponent } from "./view.component";
import { GameService } from "../../service/game.service";

@Component({
    selector: 'system',
    templateUrl: 'partial/system.html'
})
export class SystemComponent implements OnInit {
    name: string;
    system: System;
    running: boolean;

    @ViewChild(SystemViewComponent) view: SystemViewComponent;

    constructor(
            private systemService: SystemService,
            private gameService: GameService,
            private route: ActivatedRoute,
            private location: Location) {}

    ngOnInit(): void {
        this.gameService.isRunning().subscribe(running => this.running = running);

        this.route.params.map(params => params['name']).subscribe(name => {
            this.name = name;
            this.systemService.get(name).subscribe(system => this.system = system)
        });

        let timer = Observable.timer(1000, 100);
        timer.subscribe(_ => {
            if(this.running)
                this.systemService.get(this.name).subscribe(system => this.system = system);
        });
    }

    goBack(): void {
        this.location.back();
    }

    focus(name: string): void {
        this.view.focus(name);
    }
}

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs/Rx';
import { Subscription } from "rxjs/Subscription";
import 'rxjs/add/operator/switchMap';

import { SystemService } from "../../service/system.service";
import { System } from "../../model/system.model";
import { SystemViewComponent } from "./view.component";
import { GameService } from "../../service/game.service";

@Component({
    selector: 'system',
    templateUrl: 'partial/system.html'
})
export class SystemComponent implements OnInit, OnDestroy {
    name: string;
    system: System;
    running: boolean;

    subscriptions: Array<Subscription> = [];

    @ViewChild(SystemViewComponent) view: SystemViewComponent;

    constructor(
            private systemService: SystemService,
            private gameService: GameService,
            private route: ActivatedRoute,
            private location: Location) {}

    ngOnInit(): void {
        this.subscriptions.push( this.gameService.isRunning().subscribe(running => this.running = running));

        this.subscriptions.push(this.route.params.map(params => params['name']).subscribe(name => {
            const self = this;
            this.name = name;
            this.systemService.get(name, function(system): void {
                self.system = system;
            })
        }));

        let timer = Observable.timer(1000, 100);
        this.subscriptions.push(timer.subscribe(_ => {
            if(this.running) {
                const self = this;
                this.systemService.get(this.name, function(system): void {
                    self.system = system;
                })
            }

        }));
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(subscription => subscription.unsubscribe());
    }

    goBack(): void {
        this.location.back();
    }

    focus(name: string): void {
        this.view.focus(name);
    }

    pauseButton(): void {
        this.gameService.pause().first().subscribe(running => this.running = running)
    }
}

import { Component, OnInit, ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import 'rxjs/add/operator/switchMap';

import { SystemService } from "../../service/system.service";
import { System } from "../../model/system.model";
import {SystemViewComponent} from "./view.component";

@Component({
    selector: 'system',
    templateUrl: 'partial/system.html'
})
export class SystemComponent implements OnInit {
    system: System;

    @ViewChild(SystemViewComponent) view: SystemViewComponent;

    constructor(
            private systemService: SystemService,
            private route: ActivatedRoute,
            private location: Location) {}

    ngOnInit(): void {
        this.route.params.map(params => params['name']).subscribe(name =>
            this.systemService.get(name).subscribe(system => this.system = system));
    }

    goBack(): void {
        this.location.back();
    }

    focus(name: string): void {
        this.view.focus(name);
    }
}

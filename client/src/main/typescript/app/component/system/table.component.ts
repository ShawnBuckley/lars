import { Component, Input } from '@angular/core';
import { System } from "../../model/system.model";

@Component({
    selector: 'system-table',
    templateUrl: 'partial/system-table.html'
})
export class SystemTableComponent {
    @Input() system: System;
}

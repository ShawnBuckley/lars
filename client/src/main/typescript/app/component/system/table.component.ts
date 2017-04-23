import { Component, EventEmitter, Input, Output } from '@angular/core';
import { System } from "../../model/system.model";

@Component({
    selector: 'system-table',
    templateUrl: 'partial/system-table.html'
})
export class SystemTableComponent {
    @Input() system: System;

    @Output() focusEvent: EventEmitter<String> = new EventEmitter<String>();

    focus(name: String): void {
        this.focusEvent.emit(name);
    }
}

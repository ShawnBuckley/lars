import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';

import { AppComponent } from "./app.component";
import { SystemViewComponent } from "./component/system/view.component";
import { SystemTableComponent } from "./component/system/table.component";
import { SystemComponent } from "./component/system/system.component";

import { SystemService } from "./service/system.service";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        RouterModule.forRoot([
            {
                path: '',
                redirectTo: '/',
                pathMatch: 'full'
            },
            {
                path: 'system/:name',
                component: SystemComponent
            }
        ])

    ],
    declarations: [
        AppComponent,
        SystemComponent,
        SystemViewComponent,
        SystemTableComponent
    ],
    providers: [
        SystemService
    ],
    bootstrap: [ AppComponent ]
})
export class AppModule {
}
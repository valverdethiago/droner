import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';


import { AppComponent } from './app.component';
import { DroneListComponent } from './drone-list/drone-list.component';
import { DroneService } from './services/drone.service';
    


@NgModule({
  declarations: [
    AppComponent,
    DroneListComponent
  ],
  imports: [
    BrowserModule,
    HttpModule
  ],
  providers: [DroneService],
  bootstrap: [AppComponent]
})
export class AppModule { }

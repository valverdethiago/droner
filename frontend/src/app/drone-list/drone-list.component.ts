import { Component, OnInit } from '@angular/core';
import { Drone } from '../entities/drone';
import { Coordinate } from '../entities/coordinate';
import { DRONES } from './mock-drones';

@Component({
  selector: 'app-drone-list',
  templateUrl: './drone-list.component.html',
  styleUrls: ['./drone-list.component.css']
})
export class DroneListComponent implements OnInit {

  entities : Drone[];

  constructor() { }

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    console.log('Refreshing');
    this.entities = DRONES;
  }

}

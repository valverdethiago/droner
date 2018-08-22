import { Component, OnInit } from '@angular/core';
import { Drone } from '../entities/drone';
import { Coordinate } from '../entities/coordinate';
import { DroneService } from '../services/drone.service';

@Component({
  selector: 'app-drone-list',
  templateUrl: './drone-list.component.html',
  styleUrls: ['./drone-list.component.css']
})
export class DroneListComponent implements OnInit {

  entities : Drone[];

  constructor(private droneService : DroneService) { }

  ngOnInit() {
    this.refresh();
  }

  refresh() : void {
    this.droneService.searchDrones()
        .subscribe(entities => this.entities = entities);
  }

}

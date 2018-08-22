import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Drone } from '../entities/drone';
import { Coordinate } from '../entities/coordinate';
import { HttpModule, Http } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class DroneService {

  constructor(private http:Http) { }

  searchDrones(): Observable<Drone[]> {
    return this.http.get('/api/drone/all')
      .map( res => res.json() ) ;
  }

}

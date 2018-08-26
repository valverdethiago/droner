import { Coordinate } from "./coordinate";

export class Drone {
    id : String;
    status : String;
    coordinates : Coordinate[];
    lastCoordinate : Coordinate;
    velocity : number;
}
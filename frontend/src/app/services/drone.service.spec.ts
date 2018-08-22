import { TestBed, inject } from '@angular/core/testing';

import { DroneService } from './drone.service';

describe('DroneService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DroneService]
    });
  });

  it('should be created', inject([DroneService], (service: DroneService) => {
    expect(service).toBeTruthy();
  }));
});

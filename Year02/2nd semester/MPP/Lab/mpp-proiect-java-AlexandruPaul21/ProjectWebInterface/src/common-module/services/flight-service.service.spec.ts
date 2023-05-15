import { TestBed } from '@angular/core/testing';

import { FlightServiceService } from './flight-service.service';

describe('FlightServiceService', () => {
  let service: FlightServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlightServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

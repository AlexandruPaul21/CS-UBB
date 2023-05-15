import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightTableComponentComponent } from './flight-table-component.component';

describe('FlightTableComponentComponent', () => {
  let component: FlightTableComponentComponent;
  let fixture: ComponentFixture<FlightTableComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlightTableComponentComponent]
    });
    fixture = TestBed.createComponent(FlightTableComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

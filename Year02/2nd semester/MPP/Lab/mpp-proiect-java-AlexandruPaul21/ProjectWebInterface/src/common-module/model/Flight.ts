export class Flight {
  id: number | undefined;
  destination: string | undefined;
  departureDateTime: Date | undefined;
  airport: string | undefined;
  availableSeats: number | undefined;

  constructor(
    id: number | undefined,
    destination: string | undefined,
    departureDateTime: Date | undefined,
    airport: string | undefined,
    availableSeats: number | undefined
  ) {
    this.id = id;
    this.destination = destination;
    this.departureDateTime = departureDateTime;
    this.airport = airport;
    this.availableSeats = availableSeats;
  }
}

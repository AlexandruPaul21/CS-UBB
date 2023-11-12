export class Flight {
  id: number | undefined;
  plane: string | undefined;
  destination: string | undefined;
  estimatedDeparture: Date | undefined;
  canceled: boolean = false;
}

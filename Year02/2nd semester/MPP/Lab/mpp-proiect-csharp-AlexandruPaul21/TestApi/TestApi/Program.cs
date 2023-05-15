using model;
using Org.Example.Start;

var client = new RestTest();

// await client.Delete("3");

var flights = await client.GetAll();

for (int i = 0; i < flights.Length; i++)
{
    Console.WriteLine(flights[i].ToString());
}

var flight = await client.GetById(8);
Console.WriteLine(flight.ToString());

var newFlight = new Flight("New York", DateTime.Parse("12.10.2022 12:00:00"), "JFK", 100);
newFlight.Id = 2;
// var createdFlight = await client.Create(newFlight);
// Console.WriteLine(createdFlight.ToString());

// await client.Update(newFlight);
using model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    public class DtoUtils
    {
        public static AgencyDto getDto(Agency agency)
        {
            String username = agency.Id;
            String name = agency.Name;
            String password = agency.Password;
            return new AgencyDto(username, name, password);
        }

        public static Agency fromDto(AgencyDto agency)
        {
            String username = agency.username;
            String name = agency.name;
            String password = agency.password;
            var ag = new Agency(name, password);
            ag.Id = username;
            return ag;
        }

        public static Flight fromDto(FlightDto flightDto)
        {
            Flight flight = new Flight(flightDto.destination, DateTime.Parse(flightDto.departure), flightDto.airport, Int32.Parse(flightDto.avbSeats));
            flight.Id = flightDto.id;
            return flight;
        }

        public static FlightDto getDto(Flight flight)
        {
            return new FlightDto(flight);
        }

        public static BookingDto getDto(Booking booking)
        {
            return new BookingDto(booking.Id, booking.Flight, booking.Client, booking.Passengers);
        }

        public static Booking fromDto(BookingDto bookingDto)
        {
            Booking booking = new Booking(bookingDto.flight, bookingDto.client, bookingDto.passengers);
            booking.Id = bookingDto.id;
            return booking;
        }

        public static Flight[] fromDto(FlightDto[] flightDtos)
        {
            Flight[] flights = new Flight[flightDtos.Length];
            for (int i = 0; i < flightDtos.Length; ++i)
            {
                flights[i] = fromDto(flightDtos[i]);
            }

            return flights;
        }

        public static FlightDto[] getDto(Flight[] flights)
        {
            FlightDto[] flightDtos = new FlightDto[flights.Length];
            for (int i = 0; i < flights.Length; ++i)
            {
                flightDtos[i] = getDto(flights[i]);
            }

            return flightDtos;
        }
    }
}

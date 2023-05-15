using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using services;
using model;

namespace networking
{
    public class ProjectClientWorker : IProjectObserver //, Runnable
    {
        private IProjectServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;
        public ProjectClientWorker(IProjectServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public void notifyAgencies()
        {
            sendResponse(new BookingUpdate());
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request)request);
                    if (response != null)
                    {
                        sendResponse((Response)response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;

            if (request is LoginRequest)
            {
                Console.WriteLine("Login request...");
                LoginRequest loginRequest = (LoginRequest)request;
                AgencyDto agencyDto = loginRequest.agencyDto;
                Agency agency = DtoUtils.fromDto(agencyDto);
                try
                {
                    lock (server)
                    {
                        server.login(agency, this);
                    }
                    return new OkResponse();
                }
                catch (ProjectException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request...");
                LogoutRequest loginRequest = (LogoutRequest)request;
                AgencyDto agencyDto = loginRequest.agencyDto;
                Agency agency = DtoUtils.fromDto(agencyDto);
                try
                {
                    lock (server)
                    {
                        server.logout(agency, this);
                    }
                    connected = false;
                    return new OkResponse();
                }
                catch (ProjectException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is AllFlightsRequest)
            {
                Console.WriteLine("All flights request");
                AllFlightsRequest all = (AllFlightsRequest)request;
                AgencyDto agencyDto = all.agency;
                Agency agency = DtoUtils.fromDto(agencyDto);
                try
                {
                    Flight[] flights;
                    lock (server)
                    {
                        flights = server.getAllFlights(agency, this);
                    }

                    FlightDto[] flightDtos = DtoUtils.getDto(flights);

                    return new FlightsResponse(flightDtos);
                } catch (ProjectException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is FilteredFlightsRequest)
            {
                Console.WriteLine("Filtered flights requested");
                FilteredFlightsRequest all = (FilteredFlightsRequest) request;

                FlightDto flightDto = all.flight;
                Flight flight = DtoUtils.fromDto(flightDto);
                try
                {
                    Flight[] flights;
                    lock (server)
                    {
                        flights = server.getFlightsForDestinationAndDate(flight, this);
                    }

                    FlightDto[] flightDtos = DtoUtils.getDto(flights);

                    return new FlightsResponse(flightDtos);
                }
                catch (ProjectException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is NumberOfSeatsRequest)
            {
                Console.WriteLine("Number of seats request...");
                NumberOfSeatsRequest nrS = (NumberOfSeatsRequest)request;

                FlightDto flight = nrS.flight;
                Flight flight1 = DtoUtils.fromDto(flight);

                try
                {
                    int ans = 0;
                    lock (server)
                    {
                        ans = server.getNumberOfSeats(flight1, this);
                    }

                    return new SeatsResponse(ans);
                } 
                catch (ProjectException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is BookingRequest)
            {
                Console.WriteLine("Booking request...");
                BookingRequest bookingRequest = (BookingRequest)request;

                Booking booking = DtoUtils.fromDto(bookingRequest.booking);

                try
                {
                    lock (server)
                    {
                        server.bookFlight(booking, this);
                    }
                    notifyAgencies();
                    return new OkResponse();
                }
                catch (ProjectException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            return response;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            lock (stream)
            {
                formatter.Serialize(stream, response);
                stream.Flush();
            }

        }
    }


}

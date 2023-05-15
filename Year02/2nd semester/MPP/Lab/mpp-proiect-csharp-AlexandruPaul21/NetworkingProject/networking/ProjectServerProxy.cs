using model;
using services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    public class ProjectServerProxy : IProjectServices
    {
        private string host;
        private int port;

        private IProjectObserver client;

        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;
        public ProjectServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        public virtual void login(Agency agency, IProjectObserver client)
        {
            initializeConnection();
            AgencyDto udto = DtoUtils.getDto(agency);
            sendRequest(new LoginRequest(udto));
            Response response = readResponse();
            if (response is OkResponse)
            {
                this.client = client;
                return;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new ProjectException(err.Message);
            }
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new ProjectException("Error sending object " + e);
            }

        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();

                }


            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }
        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is BookingUpdate) 
                    {
                        try
                        {
                            client.notifyAgencies();
                        } 
                        catch (ProjectException e)
                        {
                            Console.WriteLine(e.StackTrace);
                        }
                    }
                    else
                    {

                        lock (responses)
                        {
                            responses.Enqueue((Response)response);

                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }

            }
        }

        public void logout(Agency agency, IProjectObserver client)
        {
            AgencyDto dto = DtoUtils.getDto(agency);
            sendRequest(new LogoutRequest(dto));
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ProjectException(err.Message);
            }
        }

        public Flight[] getAllFlights(Agency agency, IProjectObserver client)
        {
            AgencyDto agencyDto = DtoUtils.getDto(agency);
            Request request = new AllFlightsRequest(agencyDto);
            sendRequest(request);
            Response response = readResponse();

            if (response is FlightsResponse)
            {
                FlightsResponse flR = (FlightsResponse) response;
                FlightDto[] flightDtos = flR.flights;

                return DtoUtils.fromDto(flightDtos);
            }
            throw new ProjectException("There was an error");
        }

        public Flight[] getFlightsForDestinationAndDate(Flight flight, IProjectObserver client)
        {
            FlightDto flightDto = DtoUtils.getDto(flight);
            Request request = new FilteredFlightsRequest(flightDto);
            sendRequest(request);
            Response response = readResponse();

            if (response is FlightsResponse)
            {
                FlightsResponse flR = (FlightsResponse)response;
                FlightDto[] flightDtos = flR.flights;

                return DtoUtils.fromDto(flightDtos);
            }
            throw new ProjectException("There was an error");
        }

        public int getNumberOfSeats(Flight flight, IProjectObserver client)
        {
            FlightDto flightDto = DtoUtils.getDto(flight);
            Request request = new NumberOfSeatsRequest(flightDto);
            sendRequest(request);
            Response response = readResponse();

            if (response is SeatsResponse)
            {
                SeatsResponse seats = (SeatsResponse)response;
                int value = seats.number;

                return value;
            }
            throw new ProjectException("There was an error");
        }

        public void bookFlight(Booking booking, IProjectObserver client)
        {
            BookingDto bookingDto = DtoUtils.getDto(booking);
            Request request = new BookingRequest(bookingDto);
            sendRequest(request);
            Response response = readResponse();

            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ProjectException(err.Message);
            }
        }
    }
}

using System.Net.Sockets;
using Google.Protobuf;
using Org.Example;
using services;

namespace networking;

public class ProjectProtoWorker:IProjectObserver
{
    private IProjectServices server;
    private TcpClient connection;

    private NetworkStream stream;
    private volatile bool connected;
    
    public ProjectProtoWorker(IProjectServices server, TcpClient connection)
    {
        this.server = server;
        this.connection = connection;
        try
        {
            stream=connection.GetStream();
            connected=true;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
    
    public virtual void run()
    {
        while(connected)
        {
            try
            {
					
                ProjectRequest request = ProjectRequest.Parser.ParseDelimitedFrom(stream);
                ProjectResponse response = handleRequest(request);
                if (response != null)
                {
                    sendResponse(response);
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
            Console.WriteLine("Error "+e);
        }
    }

    private void sendResponse(ProjectResponse response)
    {
        Console.WriteLine("Sending response ...");
        lock (stream)
        {
            Console.WriteLine(response);
            response.WriteDelimitedTo(stream);
            stream.Flush();
            Console.WriteLine("Response sent ...");
        }
    }

    private ProjectResponse handleRequest(ProjectRequest request)
    {
        ProjectResponse response = null;
        if (request.Type == ProjectRequest.Types.Type.Login)
        {
            Console.WriteLine("Login request ...");
            model.Agency agency = ProtoUtils.getAgency(request);
            try
            {
                lock (server)
                {
                    server.login(agency, this);
                }
                Console.WriteLine("Login OK");
                return ProtoUtils.createOkResponse();
            }
            catch (ProjectException e)
            {
                Console.WriteLine("Login error " + e);
                connected = false;
                return ProtoUtils.createErrorResponse(e.Message);
            }
        }
        
        if (request.Type == ProjectRequest.Types.Type.Logout)
        {
            Console.WriteLine("Logout request...");
            model.Agency agency = ProtoUtils.getAgency(request);
            try
            {
                lock (server)
                {
                    server.logout(agency, this);
                }
                connected = false;
                return ProtoUtils.createOkResponse();
            }
            catch (ProjectException e)
            {
                return ProtoUtils.createErrorResponse(e.Message);
            }
        }

        if (request.Type == ProjectRequest.Types.Type.GetAllFlights)
        {
            Console.WriteLine("All flights request");
            model.Agency agency = ProtoUtils.getAgency(request);
            try
            {
                model.Flight[] flights;
                lock (server)
                {
                    flights = server.getAllFlights(agency, this);
                }

                return ProtoUtils.createGetFlightsResponse(flights);
            } catch (ProjectException e)
            {
                return ProtoUtils.createErrorResponse(e.Message);
            }
        }

        if (request.Type == ProjectRequest.Types.Type.GetFilteredFlights)
        {
            Console.WriteLine("Filtered flights requested");
            model.Flight flight = ProtoUtils.getFlight(request.Flight);
            try
            {
                model.Flight[] flights;
                lock (server)
                {
                    flights = server.getFlightsForDestinationAndDate(flight, this);
                }

                return ProtoUtils.createGetFilteredFlightsResponse(flights);
            }
            catch (ProjectException e)
            {
                return ProtoUtils.createErrorResponse(e.Message);
            }
        }

        if (request.Type == ProjectRequest.Types.Type.GetNumberOfSeats)
        {
            Console.WriteLine("Number of seats request...");
            
            model.Flight flight = ProtoUtils.getFlight(request.Flight);
            try
            {
                int ans = 0;
                lock (server)
                {
                    ans = server.getNumberOfSeats(flight, this);
                }

                return ProtoUtils.createNumberOfSeatsResponse(ans);
            } 
            catch (ProjectException e)
            {
                return ProtoUtils.createErrorResponse(e.Message);
            }
        }

        if (request.Type == ProjectRequest.Types.Type.BookTicket)
        {
            Console.WriteLine("Booking request...");
            model.Booking booking = ProtoUtils.getBooking(request);

            try
            {
                lock (server)
                {
                    server.bookFlight(booking, this);
                }
                notifyAgencies();
                return ProtoUtils.createOkResponse();
            }
            catch (ProjectException e)
            {
                return ProtoUtils.createErrorResponse(e.Message);
            }
        }
        
        return response;
    }

    public void notifyAgencies()
    {
        sendResponse(ProtoUtils.createUpdateResponse());
    }
}
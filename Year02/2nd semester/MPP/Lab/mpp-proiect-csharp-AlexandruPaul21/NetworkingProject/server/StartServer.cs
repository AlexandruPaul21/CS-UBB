using networking;
using persistance;
using services;
using System.Net.Sockets;

namespace server;

class StartServer
{
    static void Main(string[] args)
    {
        AgencyRepository agencyRepository = new AgencyRepository();
        FlightRepository flightRepository = new FlightRepository();
        BookingRepository bookingRepository = new BookingRepository();
        ClientRepository clientRepository = new ClientRepository();

        IProjectServices projectServices = new ProjectServicesImplementation(agencyRepository, bookingRepository, clientRepository, flightRepository);

        SerialProjectServer server = new SerialProjectServer("127.0.0.1", 5555, projectServices);

        server.Start();
        Console.WriteLine("Server started...");
        Console.ReadLine();
    }
}

public class SerialProjectServer : ConcurrentServer
{
    private IProjectServices server;
    private ProjectClientWorker worker;
    public SerialProjectServer(string host, int port, IProjectServices server) : base(host, port)
    {
        this.server = server;
        Console.WriteLine("SerialProjectServer...");
    }
    protected override Thread createWorker(TcpClient client)
    {
        worker = new ProjectClientWorker(server, client);
        return new Thread(new ThreadStart(worker.run));
    }
}
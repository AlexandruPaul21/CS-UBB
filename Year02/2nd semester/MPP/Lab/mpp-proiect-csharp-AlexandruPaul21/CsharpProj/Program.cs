using System.Reflection;
using System.Xml;
using CsharpProj.repository;
using CsharpProj.service;
using log4net;

//configure log4net
var configFilePath = "/Users/alexandrusirbu/RiderProjects/mpp-proiect-csharp-AlexandruPaul21/CsharpProj/log4netCustom.xml";
var log4netConfig = new XmlDocument();
log4netConfig.Load(File.OpenRead(configFilePath));

var repo = LogManager.CreateRepository(Assembly.GetEntryAssembly(), typeof(log4net.Repository.Hierarchy.Hierarchy));

var log4NetXml = log4netConfig["MyCustomSetting"]?["log4net"];
log4net.Config.XmlConfigurator.Configure(repo, log4NetXml);

AgencyRepository agencyRepository = new AgencyRepository();
FlightRepository flightRepository = new FlightRepository();
BookingRepository bookingRepository = new BookingRepository();
ClientRepository clientRepository = new ClientRepository();

Service service = new Service(agencyRepository, bookingRepository, clientRepository, flightRepository);

var flights = service.getAllFlights();

foreach (var flight in flights)
{
    Console.WriteLine(flight);
}

flights = service.findFlightByDestinationAndDate("Puerto Rico", DateTime.Parse("2023-03-17 12:00:00"));

foreach(var flight in flights)
{
    Console.WriteLine(flight);
}

Console.WriteLine(service.validateLogin("myAgency1", "1234"));
Console.WriteLine(service.validateLogin("myAgency1", "123"));

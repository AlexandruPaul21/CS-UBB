using CSharpInterface.repository;
using CSharpInterface.service;
using log4net;
using Microsoft.VisualBasic.Logging;

namespace CSharpInterface
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // To customize application configuration such as set high DPI settings or default font,
            // see https://aka.ms/applicationconfiguration.

            ILog logger = LogManager.GetLogger(typeof(Program));    

            AgencyRepository agencyRepository = new AgencyRepository();
            FlightRepository flightRepository = new FlightRepository();
            BookingRepository bookingRepository = new BookingRepository();
            ClientRepository clientRepository = new ClientRepository();

            Service service = new Service(agencyRepository, bookingRepository, clientRepository, flightRepository);

            ApplicationConfiguration.Initialize();

            login Login = new login();

            Login.setService(service);

            logger.Info("Application strated");
            Application.Run(Login);
        }
    }
}
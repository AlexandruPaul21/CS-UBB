using Microsoft.VisualBasic.ApplicationServices;
using model;
using services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace client
{
    public class ProjectClientCtrl : IProjectObserver
    {
        public event EventHandler<ProjectEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly IProjectServices server;
        private Agency currentUser;
        public ProjectClientCtrl(IProjectServices server)
        {
            this.server = server;
            currentUser = null;
        }

        public void login(String userId, String pass)
        {
            Agency agency = new Agency("", pass);
            agency.Id = userId;
            server.login(agency, this);
            Console.WriteLine("Login succeeded ....");
            currentUser = agency;
            Console.WriteLine("Current user {0}", agency);
        }

        public void logout()
        {
            Console.WriteLine("Logout...");
            server.logout(currentUser, this);
            currentUser = null; 
        }

        public List<Flight> getAllFlight()
        {
            return server.getAllFlights(currentUser, this).OfType<Flight>().ToList();
        } 

        public List<Flight> filterFlights(String destination, DateTime date)
        {
            Flight flight = new Flight(destination, date, "", -1);
            return server.getFlightsForDestinationAndDate(flight, this).OfType<Flight>().ToList();
        }

        public int getAvbSeats(long flightId)
        {
            Flight fl = new Flight("", DateTime.Today, "", -1);
            fl.Id = flightId;
            return server.getNumberOfSeats(fl, this);
        }

        public void book(Booking booking)
        {
            server.bookFlight(booking, this);
        }

        public void notifyAgencies()
        {
            ProjectEventArgs projectEvent = new ProjectEventArgs(ProjectEvent.Booked, new Object());
            OnUserEvent(projectEvent);
        }
        protected virtual void OnUserEvent(ProjectEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }
    }
}

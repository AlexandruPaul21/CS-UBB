using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using model;
using services;

namespace client
{
    public partial class main : Form
    {
        private ProjectClientCtrl ctrl;
        private List<Flight> displayedFlights;
        private Flight displayedFlight;
        public main(ProjectClientCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
            displayedFlights = ctrl.getAllFlight();
            this.dataGridViewFlights.DataSource = displayedFlights;

            ctrl.updateEvent += userUpdate;
        }

        public void userUpdate(object sender, ProjectEventArgs e)
        {
            dataGridViewFlights.BeginInvoke(new UpdateFlightsCallback(this.updateFlights));
        }

        private void updateFlights()
        {
            var avbSeats = ctrl.getAvbSeats(displayedFlight.Id);

            string message = "The selected flights has destination " + displayedFlight.Destination + " and " + avbSeats + " seats booked";
            availableSeatsLabel.Text = message;
        }

        public delegate void UpdateFlightsCallback();

        private void searchButton_Click(object sender, EventArgs e)
        {
            string destination = destinationTextBox.Text;
            DateTime dateTime = dateTimePicker.Value;

            this.dataGridViewFlights.DataSource = ctrl.filterFlights(destination, dateTime);
        }

        private void clearButton_Click(object sender, EventArgs e)
        {
            this.dataGridViewFlights.DataSource = ctrl.getAllFlight();
        }

        private void bookButton_Click(object sender, EventArgs e)
        {
            int selectedRowIndex = dataGridViewFlights.SelectedCells[0].RowIndex;

            if (selectedRowIndex == -1)
            {
                MessageBox.Show("You did not select any flight", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            DataGridViewRow data = dataGridViewFlights.Rows[selectedRowIndex];
            long flightId = (long)data.Cells["id"].Value;

            if (flightId < 0)
            {
                MessageBox.Show("There was an error", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            Flight flight = displayedFlights[0];

            foreach (var fl in displayedFlights)
            {
                if (fl.Id == flightId)
                {
                    flight = fl;
                }
            }

            string name = nameTextBox.Text;
            string address = addressTextBox.Text;
            string passString = passangersTextBox.Text;
            List<string> passList = passString.Split(',').ToList();

            Client client = new Client(name, address);

            Booking booking = new Booking(flight, client, passList);

            try
            {
                ctrl.book(booking);
                clearButton_Click(sender, e);
                MessageBox.Show("Succesfully booked the flight!", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            catch (ProjectException ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void dataGridViewFlights_MouseClick(object sender, MouseEventArgs e)
        {
            int row = dataGridViewFlights.SelectedCells[0].RowIndex;
            if (row < 0)
            {
                return;
            }
            DataGridViewRow data = dataGridViewFlights.Rows[row];
            long id = (long)data.Cells["id"].Value;
            var avbSeats = ctrl.getAvbSeats(id);

            Flight flight = displayedFlights[0];

            foreach (var fl in displayedFlights)
            {
                if (fl.Id == id)
                {
                    flight = fl;
                }
            }

            displayedFlight = flight;


            string message = "The selected flights has destination " + displayedFlight.Destination + " and " + avbSeats + " seats booked";
            availableSeatsLabel.Text = message;
        }

        private void main_FormClosed(object sender, FormClosedEventArgs e)
        {
            ctrl.logout();
        }
    }
}

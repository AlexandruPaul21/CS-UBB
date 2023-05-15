using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using CSharpInterface.model;
using CSharpInterface.service;

namespace CSharpInterface.gui
{
    public partial class main : Form
    {
        private Service service;
        public main()
        {
            InitializeComponent();
        }

        public void setService(Service service)
        {
            this.service = service;
            dataGridViewFlights.DataSource = service.getAllFlights();
        }

        private void searchButton_Click(object sender, EventArgs e)
        {
            string destination = destinationTextBox.Text;
            DateTime dateTime = dateTimePicker.Value;

            dataGridViewFlights.DataSource = service.findFlightByDestinationAndDate(destination, dateTime);
        }

        private void clearButton_Click(object sender, EventArgs e)
        {
            dataGridViewFlights.DataSource = service.getAllFlights();
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

            string name = nameTextBox.Text;
            string address = addressTextBox.Text;
            string passString = passangersTextBox.Text;
            List<string> passList = passString.Split(',').ToList();

            if (service.bookFlight(flightId, name, address, passList))
            {
                clearButton_Click(sender, e);
                MessageBox.Show("Succesfully booked the flight!", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            else
            {
                MessageBox.Show("The transaction could not be completed! Try again!", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
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
            var avbSeats = service.getNumberOfBookingsForFlight(id);

            string message = "The selected flights has " + avbSeats + " seats booked";
            availableSeatsLabel.Text = message;
        }
    }
}

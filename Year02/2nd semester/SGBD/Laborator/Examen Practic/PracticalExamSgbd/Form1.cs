using Microsoft.Data.SqlClient;
using System.Data;

namespace PracticalExamSgbd
{
    public partial class Form1 : Form
    {
        string connectionString = @"Server=ALEXANDRUSIBAB9;Database=politie;
                Integrated Security=true;TrustServerCertificate=true;";
        DataSet ds = new DataSet();
        SqlDataAdapter parentAdapter = new SqlDataAdapter();
        SqlDataAdapter childAdapter = new SqlDataAdapter();
        BindingSource parentBS = new BindingSource();
        BindingSource childBS = new BindingSource();
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    parentAdapter.SelectCommand = new SqlCommand("SELECT * FROM " +
                        "Sectoare;", connection);
                    parentAdapter.Fill(ds, "Sectoare"); // table name
                    childAdapter.SelectCommand = new SqlCommand("SELECT * FROM " +
                        "Programari", connection);
                    childAdapter.Fill(ds, "Programari"); // table name
                    DataColumn parentColumn = ds.Tables["Sectoare"].Columns["id"];
                    DataColumn childColumn = ds.Tables["Programari"].Columns["id_sector"];
                    DataRelation relation = new DataRelation("FK_Sectoare_Programari",
                        parentColumn, childColumn);
                    ds.Relations.Add(relation);
                    parentBS.DataSource = ds.Tables["Sectoare"];
                    dataGridViewParent.DataSource = parentBS;
                    childBS.DataSource = parentBS;
                    childBS.DataMember = "FK_Sectoare_Programari";
                    dataGridViewChild.DataSource = childBS;
                    connection.Close();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void addButton_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    int id = int.Parse(idTextBox.Text);
                    int idPolitist = int.Parse(idPolitistTextBox.Text);
                    int idSector = int.Parse(idSectorTextBox.Text);
                    string ora = oraTextBox.Text;
                    DateTime date = dataDateTimePicker.Value;
                    SqlCommand insertCommand = new SqlCommand("INSERT INTO Programari(id,id_politist,id_sector,data,ora) VALUES" +
                        " (@id, @id_politist, @id_sector, @data, @ora)", connection);
                    insertCommand.Parameters.AddWithValue("@id", id);
                    insertCommand.Parameters.AddWithValue("@id_politist", idPolitist);
                    insertCommand.Parameters.AddWithValue("@id_sector", idSector);
                    insertCommand.Parameters.AddWithValue("@data", date);
                    insertCommand.Parameters.AddWithValue("@ora", ora);
                    int insertRowCount = insertCommand.ExecuteNonQuery();
                    MessageBox.Show("Number of inserted teams: " + insertRowCount);
                    reloadChildTable(connection);
                    connection.Close();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void updateButton_Click(object sender, EventArgs e)
        {
            if (dataGridViewChild.SelectedCells.Count > 0)
            {
                try
                {
                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();
                        int id = int.Parse(idTextBox.Text);
                        int idPolitist = int.Parse(idPolitistTextBox.Text);
                        int idSector = int.Parse(idSectorTextBox.Text);
                        string ora = oraTextBox.Text;
                        DateTime date = dataDateTimePicker.Value;
                        SqlCommand updateCommand = new SqlCommand("UPDATE Programari SET id_politist=@id_politist, " +
                            "data=@data, ora=@ora WHERE id=@id;", connection);
                        updateCommand.Parameters.AddWithValue("@id", id);
                        updateCommand.Parameters.AddWithValue("@id_politist", idPolitist);
                        updateCommand.Parameters.AddWithValue("@id_sector", idSector);
                        updateCommand.Parameters.AddWithValue("@data", date);
                        updateCommand.Parameters.AddWithValue("@ora", ora);
                        int updateRowCount = updateCommand.ExecuteNonQuery();
                        MessageBox.Show("Number of updated teams: " + updateRowCount);
                        reloadChildTable(connection);
                        connection.Close();
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void deleteButton_Click(object sender, EventArgs e)
        {
            if (dataGridViewChild.SelectedCells.Count > 0)
            {
                try
                {
                    int selectedrowindex = dataGridViewChild.SelectedCells[0].RowIndex;
                    DataGridViewRow selectedRow = dataGridViewChild.Rows[selectedrowindex];
                    string id_team = Convert.ToString(selectedRow.Cells["id"].Value);

                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();
                        SqlCommand deleteCommand = new SqlCommand("DELETE FROM Programari WHERE id=@id;", connection);
                        deleteCommand.Parameters.AddWithValue("@id", id_team);
                        int deleteRowCount = deleteCommand.ExecuteNonQuery();
                        MessageBox.Show("Number of deleted teams: " + deleteRowCount);
                        reloadChildTable(connection);
                        connection.Close();
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void reloadChildTable(SqlConnection connection)
        {
            ds.Tables["Programari"].Clear();
            childAdapter.SelectCommand = new SqlCommand("SELECT * FROM " +
                "Programari", connection);
            childAdapter.Fill(ds, "Programari");
        }
    }
}
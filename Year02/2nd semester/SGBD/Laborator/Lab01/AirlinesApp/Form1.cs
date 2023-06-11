using Microsoft.Data.SqlClient;
using System.Data;
using System.Windows.Forms;

namespace ExamenPractic
{
    public partial class Form1 : Form
    {
        string connectionString = @"Server=ALEXANDRUSIBAB9;Database=politisti;
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
                    DataColumn parentColumn = ds.Tables["Sectoare"].Columns["id_pilot"];
                    DataColumn childColumn = ds.Tables["Programari"].Columns["id_pilot"];
                    DataRelation relation = new DataRelation("FK_Team_id_pilot",
                        parentColumn, childColumn);
                    ds.Relations.Add(relation);
                    parentBS.DataSource = ds.Tables["Pilots"];
                    dataGridViewParent.DataSource = parentBS;
                    childBS.DataSource = parentBS;
                    childBS.DataMember = "FK_Team_id_pilot";
                    dataGridViewChild.DataSource = childBS;
                    connection.Close();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            if (dataGridViewChild.SelectedCells.Count > 0)
            {
                try
                {
                    int selectedrowindex = dataGridViewChild.SelectedCells[0].RowIndex;
                    DataGridViewRow selectedRow = dataGridViewChild.Rows[selectedrowindex];
                    string id_team = Convert.ToString(selectedRow.Cells["id_team"].Value);

                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();
                        SqlCommand deleteCommand = new SqlCommand("DELETE FROM Team WHERE id_team=@id;", connection);
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

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            if (dataGridViewChild.SelectedCells.Count > 0)
            {
                try
                {
                    int selectedrowindex = dataGridViewChild.SelectedCells[0].RowIndex;
                    DataGridViewRow selectedRow = dataGridViewChild.Rows[selectedrowindex];
                    string id_team = Convert.ToString(selectedRow.Cells["id_team"].Value);

                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();
                        int id_pilot = int.Parse(PilotTxt.Text);
                        int id_aux = int.Parse(AuxTxt.Text);
                        SqlCommand updateCommand = new SqlCommand("UPDATE Team SET id_pilot=@id_pilot, id_aux=@id_aux WHERE id_team=@id;", connection);
                        updateCommand.Parameters.AddWithValue("@id_pilot", id_pilot);
                        updateCommand.Parameters.AddWithValue("@id_aux", id_aux);
                        updateCommand.Parameters.AddWithValue("@id", id_team);
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

        private void buttonAdd_Click(object sender, EventArgs e)
        {

            try
            {
                int selectedrowindex = dataGridViewParent.SelectedCells[0].RowIndex;
                DataGridViewRow selectedRow = dataGridViewParent.Rows[selectedrowindex];
                string id_pilot = Convert.ToString(selectedRow.Cells["id_pilot"].Value);

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    int id_aux = int.Parse(AuxTxt.Text);
                    SqlCommand insertCommand = new SqlCommand("INSERT INTO Team (id_pilot, id_aux) VALUES (@idP, @idA);", connection);
                    insertCommand.Parameters.AddWithValue("@idP", id_pilot);
                    insertCommand.Parameters.AddWithValue("@idA", id_aux);
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

        private void reloadChildTable(SqlConnection connection)
        {
            ds.Tables["Team"].Clear();
            childAdapter.SelectCommand = new SqlCommand("SELECT * FROM " +
                "Team", connection);
            childAdapter.Fill(ds, "Team");
        }
    }
}
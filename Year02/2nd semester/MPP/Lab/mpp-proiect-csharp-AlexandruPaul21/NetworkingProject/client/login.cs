using Microsoft.VisualBasic.ApplicationServices;

namespace client
{
    public partial class login : Form
    {
        private ProjectClientCtrl ctrl;
        public login(ProjectClientCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            string username = userTextBox.Text;
            string password = passwordTextBox.Text;

            try
            {
                ctrl.login(username, password);
                main chatWin = new main(ctrl);
                chatWin.Text = "Chat window for " + username;
                chatWin.Show();
                this.Hide();
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Login Error " + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }
    }
}
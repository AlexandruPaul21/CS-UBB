using CSharpInterface.gui;
using CSharpInterface.service;

namespace CSharpInterface
{
    public partial class login : Form
    {
        private Service service;

        public login()
        {
            InitializeComponent();
        }

        public void setService(Service service)
        {
            this.service = service;
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            string username = userTextBox.Text;
            string password = passwordTextBox.Text;

            if (service.validateLogin(username, password))
            {
                MessageBox.Show("Login succesfull!", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
                main Main = new main();
                Main.setService(service);
                Main.Show(this);
                userTextBox.Clear();
                passwordTextBox.Clear();
            }
            else
            {
                MessageBox.Show("There is no user with this credetials! Try again!", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
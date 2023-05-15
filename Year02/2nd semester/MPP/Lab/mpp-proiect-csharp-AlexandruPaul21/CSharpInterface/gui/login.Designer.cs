namespace CSharpInterface
{
    partial class login
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            loginButton = new Button();
            helloLabel = new Label();
            userTextBox = new TextBox();
            passwordTextBox = new TextBox();
            SuspendLayout();
            // 
            // loginButton
            // 
            loginButton.Location = new Point(88, 258);
            loginButton.Name = "loginButton";
            loginButton.Size = new Size(75, 23);
            loginButton.TabIndex = 2;
            loginButton.Text = "Login";
            loginButton.UseVisualStyleBackColor = true;
            loginButton.Click += loginButton_Click;
            // 
            // helloLabel
            // 
            helloLabel.AutoSize = true;
            helloLabel.Font = new Font("Segoe UI", 24F, FontStyle.Regular, GraphicsUnit.Point);
            helloLabel.Location = new Point(75, 64);
            helloLabel.Name = "helloLabel";
            helloLabel.Size = new Size(104, 45);
            helloLabel.TabIndex = 1;
            helloLabel.Text = "Hello!";
            // 
            // userTextBox
            // 
            userTextBox.Location = new Point(60, 146);
            userTextBox.Name = "userTextBox";
            userTextBox.PlaceholderText = "Username";
            userTextBox.Size = new Size(134, 23);
            userTextBox.TabIndex = 0;
            // 
            // passwordTextBox
            // 
            passwordTextBox.Location = new Point(60, 194);
            passwordTextBox.Name = "passwordTextBox";
            passwordTextBox.PlaceholderText = "Password";
            passwordTextBox.Size = new Size(134, 23);
            passwordTextBox.TabIndex = 1;
            passwordTextBox.UseSystemPasswordChar = true;
            // 
            // login
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(258, 343);
            Controls.Add(passwordTextBox);
            Controls.Add(userTextBox);
            Controls.Add(helloLabel);
            Controls.Add(loginButton);
            Name = "login";
            Text = "Form1";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button loginButton;
        private Label helloLabel;
        private TextBox userTextBox;
        private TextBox passwordTextBox;
    }
}
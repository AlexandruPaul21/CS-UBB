namespace CSharpInterface.gui
{
    partial class main
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
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
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            dateTimePicker = new DateTimePicker();
            searchButton = new Button();
            destinationTextBox = new TextBox();
            clearButton = new Button();
            dataGridViewFlights = new DataGridView();
            nameTextBox = new TextBox();
            addressTextBox = new TextBox();
            passangersTextBox = new RichTextBox();
            bookButton = new Button();
            label1 = new Label();
            availableSeatsLabel = new Label();
            ((System.ComponentModel.ISupportInitialize)dataGridViewFlights).BeginInit();
            SuspendLayout();
            // 
            // dateTimePicker
            // 
            dateTimePicker.Location = new Point(12, 12);
            dateTimePicker.Name = "dateTimePicker";
            dateTimePicker.Size = new Size(200, 23);
            dateTimePicker.TabIndex = 0;
            // 
            // searchButton
            // 
            searchButton.Location = new Point(409, 12);
            searchButton.Name = "searchButton";
            searchButton.Size = new Size(75, 23);
            searchButton.TabIndex = 1;
            searchButton.Text = "Search";
            searchButton.UseVisualStyleBackColor = true;
            searchButton.Click += searchButton_Click;
            // 
            // destinationTextBox
            // 
            destinationTextBox.Location = new Point(243, 12);
            destinationTextBox.Name = "destinationTextBox";
            destinationTextBox.PlaceholderText = "Destination";
            destinationTextBox.Size = new Size(143, 23);
            destinationTextBox.TabIndex = 2;
            // 
            // clearButton
            // 
            clearButton.Location = new Point(500, 11);
            clearButton.Name = "clearButton";
            clearButton.Size = new Size(75, 23);
            clearButton.TabIndex = 3;
            clearButton.Text = "Clear";
            clearButton.UseVisualStyleBackColor = true;
            clearButton.Click += clearButton_Click;
            // 
            // dataGridViewFlights
            // 
            dataGridViewFlights.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewFlights.Location = new Point(12, 57);
            dataGridViewFlights.Name = "dataGridViewFlights";
            dataGridViewFlights.RowTemplate.Height = 25;
            dataGridViewFlights.Size = new Size(563, 225);
            dataGridViewFlights.TabIndex = 4;
            dataGridViewFlights.MouseClick += dataGridViewFlights_MouseClick;
            // 
            // nameTextBox
            // 
            nameTextBox.Location = new Point(12, 333);
            nameTextBox.Name = "nameTextBox";
            nameTextBox.PlaceholderText = "Name";
            nameTextBox.Size = new Size(175, 23);
            nameTextBox.TabIndex = 5;
            // 
            // addressTextBox
            // 
            addressTextBox.Location = new Point(12, 384);
            addressTextBox.Name = "addressTextBox";
            addressTextBox.PlaceholderText = "Address";
            addressTextBox.Size = new Size(175, 23);
            addressTextBox.TabIndex = 6;
            // 
            // passangersTextBox
            // 
            passangersTextBox.Location = new Point(243, 333);
            passangersTextBox.Name = "passangersTextBox";
            passangersTextBox.Size = new Size(332, 74);
            passangersTextBox.TabIndex = 7;
            passangersTextBox.Text = "";
            // 
            // bookButton
            // 
            bookButton.Location = new Point(243, 415);
            bookButton.Name = "bookButton";
            bookButton.Size = new Size(75, 23);
            bookButton.TabIndex = 8;
            bookButton.Text = "Book";
            bookButton.UseVisualStyleBackColor = true;
            bookButton.Click += bookButton_Click;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(243, 315);
            label1.Name = "label1";
            label1.Size = new Size(65, 15);
            label1.TabIndex = 9;
            label1.Text = "Passagenrs";
            // 
            // availableSeatsLabel
            // 
            availableSeatsLabel.AutoSize = true;
            availableSeatsLabel.Location = new Point(12, 285);
            availableSeatsLabel.Name = "availableSeatsLabel";
            availableSeatsLabel.Size = new Size(0, 15);
            availableSeatsLabel.TabIndex = 10;
            // 
            // main
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(589, 450);
            Controls.Add(availableSeatsLabel);
            Controls.Add(label1);
            Controls.Add(bookButton);
            Controls.Add(passangersTextBox);
            Controls.Add(addressTextBox);
            Controls.Add(nameTextBox);
            Controls.Add(dataGridViewFlights);
            Controls.Add(clearButton);
            Controls.Add(destinationTextBox);
            Controls.Add(searchButton);
            Controls.Add(dateTimePicker);
            Name = "main";
            Text = "main";
            Load += main_Load;
            ((System.ComponentModel.ISupportInitialize)dataGridViewFlights).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        private void main_Load(object sender, EventArgs e)
        {
            
        }

        #endregion

        private DateTimePicker dateTimePicker;
        private Button searchButton;
        private TextBox destinationTextBox;
        private Button clearButton;
        private DataGridView dataGridViewFlights;
        private TextBox nameTextBox;
        private TextBox addressTextBox;
        private RichTextBox passangersTextBox;
        private Button bookButton;
        private Label label1;
        private Label availableSeatsLabel;
    }
}
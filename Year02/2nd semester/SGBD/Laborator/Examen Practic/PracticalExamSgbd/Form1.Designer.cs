namespace PracticalExamSgbd
{
    partial class Form1
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
            dataGridViewParent = new DataGridView();
            dataGridViewChild = new DataGridView();
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            label4 = new Label();
            label5 = new Label();
            label6 = new Label();
            idTextBox = new TextBox();
            idPolitistTextBox = new TextBox();
            idSectorTextBox = new TextBox();
            dataDateTimePicker = new DateTimePicker();
            addButton = new Button();
            updateButton = new Button();
            deleteButton = new Button();
            label7 = new Label();
            oraTextBox = new TextBox();
            ((System.ComponentModel.ISupportInitialize)dataGridViewParent).BeginInit();
            ((System.ComponentModel.ISupportInitialize)dataGridViewChild).BeginInit();
            SuspendLayout();
            // 
            // dataGridViewParent
            // 
            dataGridViewParent.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewParent.Location = new Point(40, 112);
            dataGridViewParent.Name = "dataGridViewParent";
            dataGridViewParent.RowHeadersWidth = 82;
            dataGridViewParent.RowTemplate.Height = 41;
            dataGridViewParent.Size = new Size(650, 356);
            dataGridViewParent.TabIndex = 0;
            // 
            // dataGridViewChild
            // 
            dataGridViewChild.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewChild.Location = new Point(730, 112);
            dataGridViewChild.Name = "dataGridViewChild";
            dataGridViewChild.RowHeadersWidth = 82;
            dataGridViewChild.RowTemplate.Height = 41;
            dataGridViewChild.Size = new Size(738, 356);
            dataGridViewChild.TabIndex = 1;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(40, 61);
            label1.Name = "label1";
            label1.Size = new Size(106, 32);
            label1.TabIndex = 2;
            label1.Text = "Sectoare";
            label1.Click += label1_Click;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(730, 61);
            label2.Name = "label2";
            label2.Size = new Size(130, 32);
            label2.TabIndex = 3;
            label2.Text = "Programari";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(730, 501);
            label3.Name = "label3";
            label3.Size = new Size(34, 32);
            label3.TabIndex = 4;
            label3.Text = "Id";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(730, 551);
            label4.Name = "label4";
            label4.Size = new Size(111, 32);
            label4.TabIndex = 5;
            label4.Text = "Id Politist";
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(733, 606);
            label5.Name = "label5";
            label5.Size = new Size(108, 32);
            label5.TabIndex = 6;
            label5.Text = "Id Sector";
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Location = new Point(733, 661);
            label6.Name = "label6";
            label6.Size = new Size(63, 32);
            label6.TabIndex = 7;
            label6.Text = "Data";
            // 
            // idTextBox
            // 
            idTextBox.Location = new Point(927, 501);
            idTextBox.Name = "idTextBox";
            idTextBox.Size = new Size(404, 39);
            idTextBox.TabIndex = 8;
            // 
            // idPolitistTextBox
            // 
            idPolitistTextBox.Location = new Point(927, 551);
            idPolitistTextBox.Name = "idPolitistTextBox";
            idPolitistTextBox.Size = new Size(404, 39);
            idPolitistTextBox.TabIndex = 9;
            // 
            // idSectorTextBox
            // 
            idSectorTextBox.Location = new Point(927, 599);
            idSectorTextBox.Name = "idSectorTextBox";
            idSectorTextBox.Size = new Size(400, 39);
            idSectorTextBox.TabIndex = 10;
            // 
            // dataDateTimePicker
            // 
            dataDateTimePicker.Location = new Point(927, 656);
            dataDateTimePicker.Name = "dataDateTimePicker";
            dataDateTimePicker.Size = new Size(400, 39);
            dataDateTimePicker.TabIndex = 11;
            // 
            // addButton
            // 
            addButton.Location = new Point(733, 769);
            addButton.Name = "addButton";
            addButton.Size = new Size(150, 46);
            addButton.TabIndex = 12;
            addButton.Text = "Add";
            addButton.UseVisualStyleBackColor = true;
            addButton.Click += addButton_Click;
            // 
            // updateButton
            // 
            updateButton.Location = new Point(993, 769);
            updateButton.Name = "updateButton";
            updateButton.Size = new Size(150, 46);
            updateButton.TabIndex = 13;
            updateButton.Text = "Update";
            updateButton.UseVisualStyleBackColor = true;
            updateButton.Click += updateButton_Click;
            // 
            // deleteButton
            // 
            deleteButton.Location = new Point(1229, 769);
            deleteButton.Name = "deleteButton";
            deleteButton.Size = new Size(150, 46);
            deleteButton.TabIndex = 14;
            deleteButton.Text = "Delete";
            deleteButton.UseVisualStyleBackColor = true;
            deleteButton.Click += deleteButton_Click;
            // 
            // label7
            // 
            label7.AutoSize = true;
            label7.Location = new Point(733, 721);
            label7.Name = "label7";
            label7.Size = new Size(52, 32);
            label7.TabIndex = 15;
            label7.Text = "Ora";
            // 
            // oraTextBox
            // 
            oraTextBox.Location = new Point(927, 721);
            oraTextBox.Name = "oraTextBox";
            oraTextBox.Size = new Size(404, 39);
            oraTextBox.TabIndex = 16;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(13F, 32F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1480, 894);
            Controls.Add(oraTextBox);
            Controls.Add(label7);
            Controls.Add(deleteButton);
            Controls.Add(updateButton);
            Controls.Add(addButton);
            Controls.Add(dataDateTimePicker);
            Controls.Add(idSectorTextBox);
            Controls.Add(idPolitistTextBox);
            Controls.Add(idTextBox);
            Controls.Add(label6);
            Controls.Add(label5);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(dataGridViewChild);
            Controls.Add(dataGridViewParent);
            Name = "Form1";
            Text = "Form1";
            Load += Form1_Load;
            ((System.ComponentModel.ISupportInitialize)dataGridViewParent).EndInit();
            ((System.ComponentModel.ISupportInitialize)dataGridViewChild).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private DataGridView dataGridViewParent;
        private DataGridView dataGridViewChild;
        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private Label label5;
        private Label label6;
        private TextBox idTextBox;
        private TextBox idPolitistTextBox;
        private TextBox idSectorTextBox;
        private DateTimePicker dataDateTimePicker;
        private Button addButton;
        private Button updateButton;
        private Button deleteButton;
        private Label label7;
        private TextBox oraTextBox;
    }
}
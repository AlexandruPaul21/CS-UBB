namespace ExamenPractic
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
            buttonDelete = new Button();
            buttonUpdate = new Button();
            buttonAdd = new Button();
            label3 = new Label();
            label4 = new Label();
            PilotTxt = new TextBox();
            AuxTxt = new TextBox();
            ((System.ComponentModel.ISupportInitialize)dataGridViewParent).BeginInit();
            ((System.ComponentModel.ISupportInitialize)dataGridViewChild).BeginInit();
            SuspendLayout();
            // 
            // dataGridViewParent
            // 
            dataGridViewParent.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewParent.Location = new Point(20, 144);
            dataGridViewParent.Margin = new Padding(5);
            dataGridViewParent.Name = "dataGridViewParent";
            dataGridViewParent.RowHeadersWidth = 51;
            dataGridViewParent.RowTemplate.Height = 29;
            dataGridViewParent.Size = new Size(578, 301);
            dataGridViewParent.TabIndex = 0;
            // 
            // dataGridViewChild
            // 
            dataGridViewChild.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridViewChild.Location = new Point(702, 144);
            dataGridViewChild.Margin = new Padding(5);
            dataGridViewChild.Name = "dataGridViewChild";
            dataGridViewChild.RowHeadersWidth = 51;
            dataGridViewChild.RowTemplate.Height = 29;
            dataGridViewChild.Size = new Size(578, 301);
            dataGridViewChild.TabIndex = 1;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(20, 88);
            label1.Margin = new Padding(5, 0, 5, 0);
            label1.Name = "label1";
            label1.Size = new Size(133, 32);
            label1.TabIndex = 2;
            label1.Text = "Pilots Table";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(702, 88);
            label2.Margin = new Padding(5, 0, 5, 0);
            label2.Name = "label2";
            label2.Size = new Size(143, 32);
            label2.TabIndex = 3;
            label2.Text = "Teams Table";
            // 
            // buttonDelete
            // 
            buttonDelete.Location = new Point(1116, 632);
            buttonDelete.Margin = new Padding(5);
            buttonDelete.Name = "buttonDelete";
            buttonDelete.Size = new Size(153, 46);
            buttonDelete.TabIndex = 4;
            buttonDelete.Text = "Delete";
            buttonDelete.UseVisualStyleBackColor = true;
            buttonDelete.Click += buttonDelete_Click;
            // 
            // buttonUpdate
            // 
            buttonUpdate.Location = new Point(926, 632);
            buttonUpdate.Margin = new Padding(5);
            buttonUpdate.Name = "buttonUpdate";
            buttonUpdate.Size = new Size(153, 46);
            buttonUpdate.TabIndex = 5;
            buttonUpdate.Text = "Update";
            buttonUpdate.UseVisualStyleBackColor = true;
            buttonUpdate.Click += buttonUpdate_Click;
            // 
            // buttonAdd
            // 
            buttonAdd.Location = new Point(702, 632);
            buttonAdd.Margin = new Padding(5);
            buttonAdd.Name = "buttonAdd";
            buttonAdd.Size = new Size(153, 46);
            buttonAdd.TabIndex = 6;
            buttonAdd.Text = "Add";
            buttonAdd.UseVisualStyleBackColor = true;
            buttonAdd.Click += buttonAdd_Click;
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(702, 464);
            label3.Margin = new Padding(5, 0, 5, 0);
            label3.Name = "label3";
            label3.Size = new Size(103, 32);
            label3.TabIndex = 7;
            label3.Text = "ID Pilot: ";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(702, 531);
            label4.Margin = new Padding(5, 0, 5, 0);
            label4.Name = "label4";
            label4.Size = new Size(196, 32);
            label4.TabIndex = 8;
            label4.Text = "ID auxiliary staff: ";
            // 
            // PilotTxt
            // 
            PilotTxt.Location = new Point(912, 464);
            PilotTxt.Margin = new Padding(5);
            PilotTxt.Name = "PilotTxt";
            PilotTxt.Size = new Size(201, 39);
            PilotTxt.TabIndex = 9;
            // 
            // AuxTxt
            // 
            AuxTxt.Location = new Point(912, 520);
            AuxTxt.Margin = new Padding(5);
            AuxTxt.Name = "AuxTxt";
            AuxTxt.Size = new Size(201, 39);
            AuxTxt.TabIndex = 10;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(13F, 32F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1300, 720);
            Controls.Add(AuxTxt);
            Controls.Add(PilotTxt);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(buttonAdd);
            Controls.Add(buttonUpdate);
            Controls.Add(buttonDelete);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(dataGridViewChild);
            Controls.Add(dataGridViewParent);
            Margin = new Padding(5);
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
        private Button buttonDelete;
        private Button buttonUpdate;
        private Button buttonAdd;
        private Label label3;
        private Label label4;
        private TextBox PilotTxt;
        private TextBox AuxTxt;
    }
}
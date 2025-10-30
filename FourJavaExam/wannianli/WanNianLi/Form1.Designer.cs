namespace WanNianLi
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
            components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            day = new TableLayoutPanel();
            now = new Label();
            next = new Button();
            last = new Button();
            turn = new Button();
            dateTimePicker1 = new DateTimePicker();
            timerClock = new System.Windows.Forms.Timer(components);
            timer2 = new System.Windows.Forms.Timer(components);
            pbClock = new PictureBox();
            pictureBox1 = new PictureBox();
            ((System.ComponentModel.ISupportInitialize)pbClock).BeginInit();
            ((System.ComponentModel.ISupportInitialize)pictureBox1).BeginInit();
            SuspendLayout();
            // 
            // day
            // 
            day.BackColor = Color.Transparent;
            day.ColumnCount = 7;
            day.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 14.2857113F));
            day.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 14.2857161F));
            day.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 14.2857161F));
            day.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 14.2857161F));
            day.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 14.2857161F));
            day.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 14.2857161F));
            day.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 14.2857161F));
            day.Location = new Point(96, 183);
            day.Name = "day";
            day.RowCount = 7;
            day.RowStyles.Add(new RowStyle(SizeType.Percent, 14.2857141F));
            day.RowStyles.Add(new RowStyle(SizeType.Percent, 14.2857141F));
            day.RowStyles.Add(new RowStyle(SizeType.Percent, 14.2857141F));
            day.RowStyles.Add(new RowStyle(SizeType.Percent, 14.2857141F));
            day.RowStyles.Add(new RowStyle(SizeType.Percent, 14.2857141F));
            day.RowStyles.Add(new RowStyle(SizeType.Percent, 14.2857141F));
            day.RowStyles.Add(new RowStyle(SizeType.Percent, 14.2857141F));
            day.Size = new Size(859, 401);
            day.TabIndex = 0;
            day.Paint += tableLayoutPanel1_Paint;
            // 
            // now
            // 
            now.AutoSize = true;
            now.BackColor = Color.Transparent;
            now.Font = new Font("Microsoft YaHei UI", 14.25F, FontStyle.Regular, GraphicsUnit.Point);
            now.Location = new Point(493, 79);
            now.Name = "now";
            now.Size = new Size(67, 25);
            now.TabIndex = 1;
            now.Text = "label1";
            now.Click += label1_Click;
            // 
            // next
            // 
            next.BackColor = Color.Transparent;
            next.Font = new Font("Microsoft YaHei UI", 15.75F, FontStyle.Bold, GraphicsUnit.Point);
            next.Location = new Point(701, 52);
            next.Name = "next";
            next.Size = new Size(131, 79);
            next.TabIndex = 2;
            next.Text = "下个月";
            next.UseVisualStyleBackColor = false;
            next.Click += next_Click;
            // 
            // last
            // 
            last.BackColor = Color.Transparent;
            last.Font = new Font("Microsoft YaHei UI", 15.75F, FontStyle.Bold, GraphicsUnit.Point);
            last.Location = new Point(213, 52);
            last.Name = "last";
            last.Size = new Size(131, 79);
            last.TabIndex = 3;
            last.Text = "上个月";
            last.UseVisualStyleBackColor = false;
            last.Click += last_Click;
            // 
            // turn
            // 
            turn.Location = new Point(576, 619);
            turn.Name = "turn";
            turn.Size = new Size(117, 23);
            turn.TabIndex = 5;
            turn.Text = "跳转";
            turn.UseVisualStyleBackColor = true;
            turn.Click += turn_Click;
            // 
            // dateTimePicker1
            // 
            dateTimePicker1.CalendarMonthBackground = Color.Transparent;
            dateTimePicker1.Location = new Point(348, 619);
            dateTimePicker1.MaxDate = new DateTime(8009, 12, 31, 0, 0, 0, 0);
            dateTimePicker1.Name = "dateTimePicker1";
            dateTimePicker1.Size = new Size(200, 23);
            dateTimePicker1.TabIndex = 6;
            dateTimePicker1.ValueChanged += dateTimePicker1_ValueChanged;
            // 
            // timerClock
            // 
            timerClock.Enabled = true;
            timerClock.Interval = 1000;
            timerClock.Tick += timerClock_Tick;
            // 
            // pbClock
            // 
            pbClock.BackColor = Color.Transparent;
            pbClock.Cursor = Cursors.NoMove2D;
            pbClock.Location = new Point(1003, 1);
            pbClock.Name = "pbClock";
            pbClock.Size = new Size(200, 200);
            pbClock.TabIndex = 7;
            pbClock.TabStop = false;
            // 
            // pictureBox1
            // 
            pictureBox1.BackgroundImageLayout = ImageLayout.Stretch;
            pictureBox1.Image = (Image)resources.GetObject("pictureBox1.Image");
            pictureBox1.Location = new Point(-2, 1);
            pictureBox1.Name = "pictureBox1";
            pictureBox1.Size = new Size(1235, 678);
            pictureBox1.SizeMode = PictureBoxSizeMode.StretchImage;
            pictureBox1.TabIndex = 8;
            pictureBox1.TabStop = false;
            pictureBox1.Tag = " ";
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(7F, 17F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1230, 677);
            Controls.Add(pbClock);
            Controls.Add(dateTimePicker1);
            Controls.Add(turn);
            Controls.Add(last);
            Controls.Add(next);
            Controls.Add(now);
            Controls.Add(day);
            Controls.Add(pictureBox1);
            Name = "Form1";
            Text = "Form1";
            Load += Form1_Load;
            ((System.ComponentModel.ISupportInitialize)pbClock).EndInit();
            ((System.ComponentModel.ISupportInitialize)pictureBox1).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TableLayoutPanel day;
        private Label now;
        private Button next;
        private Button last;
        private Button turn;
        private DateTimePicker dateTimePicker1;
        private System.Windows.Forms.Timer timerClock;
        private System.Windows.Forms.Timer timer2;
        private PictureBox pbClock;
        private PictureBox pictureBox1;
    }
}

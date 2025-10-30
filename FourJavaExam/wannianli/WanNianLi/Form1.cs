using Microsoft.VisualBasic.Devices;
using System;
using System.Net;
using System.Net.Sockets;
using System.Threading.Tasks;
using System.Drawing;
using System.IO;
using System.Text.Json;
using System.Windows.Forms;
using System.Diagnostics;
using System.Security.Principal;
using System.Linq;
using System.Collections.Generic;

namespace WanNianLi
{
    public partial class Form1 : Form
    {
        // 指向当前显示的日期
        private DateTime _displayMonth;
        private readonly string _dataFile = Path.Combine(Application.StartupPath, "schedule.json");
        // 存储每一天的日程列表
        private Dictionary<DateTime, List<string>> _scheduleData = new Dictionary<DateTime, List<string>>();

        public Form1()
        {
            InitializeComponent();
            this.FormClosing += (s, e) => SaveScheduleData();
        }

        private async void Form1_Load(object sender, EventArgs e)
        {
            // 加载日程
            LoadScheduleData();

            DateTime networkTime;
            try
            {
                networkTime = await GetNetworkTimeAsync("ntp.aliyun.com");
            }
            catch
            {
                MessageBox.Show("无法获取网络时间，使用本地时间。");
                networkTime = DateTime.Now;
            }

            _displayMonth = new DateTime(networkTime.Year, networkTime.Month, 1);
            dateTimePicker1.Value = networkTime;

            RefreshCalendar();
            UpdateAllScheduleDots();
        }

        private void tableLayoutPanel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void next_Click(object sender, EventArgs e)
        {
            // 1. 将当前显示月份加上一个月
            _displayMonth = _displayMonth.AddMonths(1);

            // 2. 刷新日历显示
            RefreshCalendar();

            UpdateAllScheduleDots();
        }

        private void last_Click(object sender, EventArgs e)
        {
            // 1. 将当前显示月份减去一个月
            _displayMonth = _displayMonth.AddMonths(-1);

            // 2. 刷新日历显示
            RefreshCalendar();

            UpdateAllScheduleDots();
        }

        private void dateTimePicker1_ValueChanged(object sender, EventArgs e)
        {

        }

        private void label1_Click_1(object sender, EventArgs e)
        {

        }

        private void RefreshCalendar()
        {
            // 清空已有控件（但是保留表头行的空间，先清所有再重建）
            day.SuspendLayout();
            day.Controls.Clear();

            // 1) 添加星期表头（第一行，row = 0）
            string[] headers = { "日", "一", "二", "三", "四", "五", "六" };
            for (int c = 0; c < 7; c++)
            {
                //为了美观起见，在父容器里面塞入子容器，塞满填色，把属性设置好。
                var lbl = new Label
                {
                    Text = headers[c],
                    Dock = DockStyle.Fill,
                    TextAlign = ContentAlignment.MiddleCenter,
                    Font = new Font("Microsoft YaHei UI", 10F, FontStyle.Bold),
                    BackColor = Color.LightGray,
                    Margin = new Padding(0)
                };
                //塞入指定位置
                day.Controls.Add(lbl, c, 0);
            }

            // 2) 计算当月第一天在星期中的列位置（Sunday=0）
            DateTime firstOfMonth = new DateTime(_displayMonth.Year, _displayMonth.Month, 1);
            int startCol = (int)firstOfMonth.DayOfWeek; // 0..6
            int days = DateTime.DaysInMonth(_displayMonth.Year, _displayMonth.Month);

            // 3) 将每一天放入 TableLayoutPanel 的对应 (col, row)；表头占 row 0，日期从 row 1 开始
            int cellIndex = startCol;
            for (int dayNum = 1; dayNum <= days; dayNum++, cellIndex++)
            {
                //确定行列
                int col = cellIndex % 7;
                int row = 1 + (cellIndex / 7);

                var panel = new Panel
                {
                    Dock = DockStyle.Fill,
                    Margin = new Padding(2),
                    BorderStyle = BorderStyle.Fixed3D
                };

                var btn = new Button
                {
                    Text = dayNum.ToString(),
                    Dock = DockStyle.Top,
                    Height = 40,
                    Tag = new DateTime(_displayMonth.Year, _displayMonth.Month, dayNum)
                };
                btn.Click += (s, e) => AddScheduleToDate((Button)s);
                // 高亮今天
                var dt = (DateTime)btn.Tag;
                if (dt.Date == DateTime.Today)
                {
                    btn.BackColor = Color.PaleGreen;
                }

                //btn.Click += DayButton_Click;

                // panel里面留了一些空隙，如果添加日程就塞一个小原点，而且按钮可以直接产看修改日程
                panel.Controls.Add(btn);

                day.Controls.Add(panel, col, row);
            }

            // 4) 补齐空单元格（避免 null）
            for (int r = 1; r <= 6; r++)
            {
                for (int c = 0; c < 7; c++)
                {
                    var ctrl = day.GetControlFromPosition(c, r);
                    if (ctrl == null)
                    {
                        day.Controls.Add(new Panel { Dock = DockStyle.Fill, Margin = new Padding(2) }, c, r);
                    }
                }
            }

            // 5) 更新顶部年月显示
            now.Text = _displayMonth.ToString("yyyy 年 MM 月");

            day.ResumeLayout();

        }

        private void AddScheduleToDate(Button btn)
        {
            DateTime date = (DateTime)btn.Tag;

            // 取出该日期的已有日程
            List<string> schedules;
            if (!_scheduleData.TryGetValue(date, out schedules))
            {
                schedules = new List<string>();
            }

            // 显示已有日程
            string existing = schedules.Count > 0
                ? string.Join("\n", schedules.Select((s, i) => $"{i + 1}. {s}"))
                : "（暂无日程）";

            // 提示用户
            string message = $"当前 {date:yyyy-MM-dd} 的日程如下：\n\n{existing}\n\n" +
                             "输入新的日程内容（或输入：\n" +
                             "  edit N 修改第N条，\n" +
                             "  del N 删除第N条）";

            string input = Microsoft.VisualBasic.Interaction.InputBox(
                message,
                "管理日程",
                ""
            );

            if (string.IsNullOrWhiteSpace(input))
                return; // 用户取消

            bool isNewSchedule = false;
            string newScheduleContent = "";

            // 命令解析
            if (input.StartsWith("edit ", StringComparison.OrdinalIgnoreCase))
            {
                if (int.TryParse(input.Substring(5).Trim(), out int index) &&
                    index >= 1 && index <= schedules.Count)
                {
                    string old = schedules[index - 1];
                    string newText = Microsoft.VisualBasic.Interaction.InputBox(
                        $"修改原日程：\n{old}\n\n请输入新内容：",
                        "修改日程",
                        old
                    );

                    if (!string.IsNullOrWhiteSpace(newText))
                        schedules[index - 1] = newText;
                }
            }
            else if (input.StartsWith("del ", StringComparison.OrdinalIgnoreCase))
            {
                if (int.TryParse(input.Substring(4).Trim(), out int index) &&
                    index >= 1 && index <= schedules.Count)
                {
                    if (MessageBox.Show($"确定要删除第 {index} 条日程？",
                                        "确认删除", MessageBoxButtons.YesNo) == DialogResult.Yes)
                    {
                        schedules.RemoveAt(index - 1);
                    }
                }
            }
            else
            {
                // 普通添加 - 新增日程
                schedules.Add(input);
                isNewSchedule = true;
                newScheduleContent = input;

                // 立即显示提示，用于调试
                MessageBox.Show("检测到新日程添加：" + input + "\n日期：" + date.ToString("yyyy-MM-dd"), "调试信息");
            }

            // 保存回字典
            if (schedules.Count > 0)
                _scheduleData[date] = schedules;
            else
                _scheduleData.Remove(date); // 没有日程则删除该日期记录

            // 更新按钮小圆点显示
            UpdateScheduleDot(btn, schedules.Count > 0);
            // 保存到文件
            SaveScheduleData();

            // 新增：如果是添加新日程，询问是否创建计划任务
            if (isNewSchedule)
            {
                AskCreateScheduledTask(date, newScheduleContent);
            }
        }

        /// <summary>
        /// 询问用户是否创建计划任务
        /// </summary>
        private void AskCreateScheduledTask(DateTime scheduleDate, string scheduleContent)
        {
            // 只有未来的日期或今天才提示创建计划任务
            if (scheduleDate.Date < DateTime.Today)
            {
                MessageBox.Show("过去的日期无法创建计划任务", "提示");
                return;
            }

            DialogResult result = MessageBox.Show(
                "是否为此日程创建系统计划任务？\n\n" +
                "日程内容：" + scheduleContent + "\n" +
                "日程日期：" + scheduleDate.ToString("yyyy-MM-dd") + "\n\n" +
                "创建后，系统将在该日期的 09:00 自动启动本程序提醒您。\n" +
                "（需要管理员权限）",
                "创建计划任务",
                MessageBoxButtons.YesNo,
                MessageBoxIcon.Question
            );

            if (result == DialogResult.Yes)
            {
                // 设置默认提醒时间为 09:00
                DateTime taskDateTime = scheduleDate.Date.AddHours(9);

                // 可选：让用户自定义时间
                string timeInput = Microsoft.VisualBasic.Interaction.InputBox(
                    "请输入提醒时间（格式：HH:mm，24小时制）：",
                    "设置提醒时间",
                    "09:00"
                );

                if (!string.IsNullOrWhiteSpace(timeInput))
                {
                    if (TimeSpan.TryParse(timeInput, out TimeSpan time))
                    {
                        taskDateTime = scheduleDate.Date.Add(time);
                    }
                }

                CreateScheduledTask(taskDateTime, scheduleContent);
            }
        }

        /// <summary>
        /// 创建 Windows 计划任务
        /// </summary>
        private void CreateScheduledTask(DateTime taskDateTime, string scheduleContent)
        {
            try
            {
                // 检查是否有管理员权限
                if (!IsAdministrator())
                {
                    MessageBox.Show(
                        "创建计划任务需要管理员权限！\n\n" +
                        "请以管理员身份重新运行本程序。",
                        "权限不足",
                        MessageBoxButtons.OK,
                        MessageBoxIcon.Warning
                    );

                    // 提示重启为管理员
                    DialogResult restartResult = MessageBox.Show(
                        "是否以管理员身份重新启动程序？",
                        "重新启动",
                        MessageBoxButtons.YesNo,
                        MessageBoxIcon.Question
                    );

                    if (restartResult == DialogResult.Yes)
                    {
                        RestartAsAdmin();
                    }
                    return;
                }

                // 构建任务名称（避免重复）
                string taskName = "万年历提醒_" + scheduleContent.Replace(" ", "_").Substring(0, Math.Min(20, scheduleContent.Length)) + "_" + taskDateTime.ToString("yyyyMMdd_HHmm");

                // 清理任务名称中的非法字符
                foreach (char c in Path.GetInvalidFileNameChars())
                {
                    taskName = taskName.Replace(c, '_');
                }

                // 获取当前程序路径
                string exePath = Application.ExecutablePath;

                // 格式化日期和时间
                string startDate = taskDateTime.ToString("yyyy-MM-dd");
                string startTime = taskDateTime.ToString("HH:mm");

                // 构建 schtasks 命令
                string arguments = "/create /tn \"" + taskName + "\" /tr \"\\\"" + exePath + "\\\"\" /sc once /sd " + startDate + " /st " + startTime + " /f /rl HIGHEST";

                ProcessStartInfo psi = new ProcessStartInfo
                {
                    FileName = "schtasks.exe",
                    Arguments = arguments,
                    UseShellExecute = false,
                    CreateNoWindow = true,
                    RedirectStandardOutput = true,
                    RedirectStandardError = true
                };

                using (Process process = Process.Start(psi))
                {
                    string output = process.StandardOutput.ReadToEnd();
                    string error = process.StandardError.ReadToEnd();
                    process.WaitForExit();

                    if (process.ExitCode == 0)
                    {
                        MessageBox.Show(
                            "计划任务创建成功！\n\n" +
                            "任务名称：" + taskName + "\n" +
                            "执行时间：" + taskDateTime.ToString("yyyy-MM-dd HH:mm") + "\n\n" +
                            "您可以在任务计划程序中查看和管理此任务。",
                            "成功",
                            MessageBoxButtons.OK,
                            MessageBoxIcon.Information
                        );
                    }
                    else
                    {
                        MessageBox.Show(
                            "计划任务创建失败！\n\n" +
                            "错误代码：" + process.ExitCode.ToString() + "\n" +
                            "错误信息：" + error + "\n" +
                            "输出信息：" + output,
                            "失败",
                            MessageBoxButtons.OK,
                            MessageBoxIcon.Error
                        );
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(
                    "创建计划任务时发生错误：\n\n" + ex.Message,
                    "错误",
                    MessageBoxButtons.OK,
                    MessageBoxIcon.Error
                );
            }
        }

        /// <summary>
        /// 检查当前程序是否以管理员权限运行
        /// </summary>
        private bool IsAdministrator()
        {
            try
            {
                WindowsIdentity identity = WindowsIdentity.GetCurrent();
                WindowsPrincipal principal = new WindowsPrincipal(identity);
                return principal.IsInRole(WindowsBuiltInRole.Administrator);
            }
            catch
            {
                return false;
            }
        }

        /// <summary>
        /// 以管理员身份重启程序
        /// </summary>
        private void RestartAsAdmin()
        {
            try
            {
                ProcessStartInfo psi = new ProcessStartInfo
                {
                    FileName = Application.ExecutablePath,
                    UseShellExecute = true,
                    Verb = "runas" // 请求管理员权限
                };

                Process.Start(psi);
                Application.Exit(); // 关闭当前实例
            }
            catch (Exception ex)
            {
                MessageBox.Show("无法以管理员身份启动：\n" + ex.Message, "错误");
            }
        }

        private void UpdateScheduleDot(Button btn, bool hasSchedule)
        {
            // 找到按钮所在的 Panel
            var panel = btn.Parent;
            if (panel == null) return;

            // 删除旧圆点
            foreach (Control c in panel.Controls.OfType<Label>().Where(l => l.Tag?.ToString() == "dot").ToList())
                panel.Controls.Remove(c);

            // 如果有日程，添加圆点
            if (hasSchedule)
            {
                var dot = new Label
                {
                    BackColor = Color.Red,
                    Width = 8,
                    Height = 8,
                    Tag = "dot",
                    BorderStyle = BorderStyle.None,
                    Location = new Point(panel.Width - 14, panel.Height - 14),
                    Anchor = AnchorStyles.Bottom | AnchorStyles.Right
                };
                panel.Controls.Add(dot);
                dot.BringToFront();
            }
        }

        // 保存日程数据到文件
        private void SaveScheduleData()
        {
            try
            {
                var json = JsonSerializer.Serialize(_scheduleData, new JsonSerializerOptions
                {
                    WriteIndented = true // 格式化输出，美观易读
                });
                File.WriteAllText(_dataFile, json);
            }
            catch (Exception ex)
            {
                MessageBox.Show("保存日程失败：" + ex.Message);
            }
        }

        // 从文件加载日程数据
        private void LoadScheduleData()
        {
            try
            {
                if (File.Exists(_dataFile))
                {
                    string json = File.ReadAllText(_dataFile);
                    _scheduleData = JsonSerializer.Deserialize<Dictionary<DateTime, List<string>>>(json)
                                    ?? new Dictionary<DateTime, List<string>>();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("加载日程失败：" + ex.Message);
                _scheduleData = new Dictionary<DateTime, List<string>>();
            }
        }

        private void UpdateAllScheduleDots()
        {
            foreach (Control ctrl in day.Controls)
            {
                if (ctrl is Panel panel)
                {
                    var btn = panel.Controls.OfType<Button>().FirstOrDefault();
                    if (btn != null && btn.Tag is DateTime date)
                    {
                        UpdateScheduleDot(btn, _scheduleData.ContainsKey(date));
                    }
                }
            }
        }


        private void turn_Click(object sender, EventArgs e)
        {
            // 1️⃣ 获取 DateTimePicker 选择的日期
            DateTime targetDate = dateTimePicker1.Value;

            // 2️⃣ 设置当前显示的月份为该日期所在的月份（忽略具体日）
            _displayMonth = new DateTime(targetDate.Year, targetDate.Month, 1);

            // 3️⃣ 刷新日历界面
            RefreshCalendar();

            // 4️⃣ 更新小圆点显示（显示有日程的日期）
            UpdateAllScheduleDots();
        }

        private async Task<DateTime> GetNetworkTimeAsync(string ntpServer)
        {
            byte[] ntpData = new byte[48];
            ntpData[0] = 0x1B; // NTP请求头，表示客户端模式

            // 获取 NTP 服务器 IP
            IPAddress ipAddress = (await Dns.GetHostAddressesAsync(ntpServer))
                                    .FirstOrDefault(addr => addr.AddressFamily == AddressFamily.InterNetwork);
            if (ipAddress == null)
                throw new Exception("未找到合适的 NTP 服务器地址。");

            // 使用 UDP 进行通信
            using (UdpClient client = new UdpClient())
            {
                client.Connect(ipAddress, 123); // NTP 默认端口 123
                await client.SendAsync(ntpData, ntpData.Length);
                var response = await client.ReceiveAsync();
                ntpData = response.Buffer;
            }

            // 处理字节顺序
            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(ntpData, 40, 4);
                Array.Reverse(ntpData, 44, 4);
            }

            // 提取时间数据
            ulong intPart = BitConverter.ToUInt32(ntpData, 40);
            ulong fractPart = BitConverter.ToUInt32(ntpData, 44);

            // NTP 起点是 1900-01-01 00:00:00 UTC
            DateTime networkDateTime = new DateTime(1900, 1, 1, 0, 0, 0, DateTimeKind.Utc)
                .AddSeconds(intPart)
                .AddMilliseconds(fractPart * 1000.0 / 0x100000000L)
                .ToLocalTime();

            return networkDateTime;
        }


        private async void timerClock_Tick(object sender, EventArgs e)
        {
            DateTime now;
            try
            {
                now = await GetNetworkTimeAsync("ntp.aliyun.com");
            }
            catch
            {
                now = DateTime.Now;
            }

            int width = pbClock.Width;
            int height = pbClock.Height;
            Bitmap bmp = new Bitmap(width, height);

            using (Graphics g = Graphics.FromImage(bmp))
            {
                g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
                g.Clear(Color.White);

                Point center = new Point(width / 2, height / 2);
                int radius = Math.Min(width, height) / 2 - 5;

                g.DrawEllipse(Pens.Black, center.X - radius, center.Y - radius, radius * 2, radius * 2);

                // 绘制刻度
                for (int i = 0; i < 60; i++)
                {
                    float angle = i * 6 - 90;
                    float cos = (float)Math.Cos(angle * Math.PI / 180);
                    float sin = (float)Math.Sin(angle * Math.PI / 180);
                    float innerRadius = (i % 5 == 0) ? radius * 0.85f : radius * 0.9f;
                    float outerRadius = radius;
                    float x1 = center.X + innerRadius * cos;
                    float y1 = center.Y + innerRadius * sin;
                    float x2 = center.X + outerRadius * cos;
                    float y2 = center.Y + outerRadius * sin;
                    Pen pen = (i % 5 == 0) ? new Pen(Color.Black, 2) : new Pen(Color.Black, 1);
                    g.DrawLine(pen, x1, y1, x2, y2);
                }

                // 数字
                for (int i = 1; i <= 12; i++)
                {
                    float angle = i * 30 - 90;
                    float cos = (float)Math.Cos(angle * Math.PI / 180);
                    float sin = (float)Math.Sin(angle * Math.PI / 180);
                    float numRadius = radius * 0.7f;
                    string text = i.ToString();
                    SizeF textSize = g.MeasureString(text, this.Font);
                    float x = center.X + numRadius * cos - textSize.Width / 2;
                    float y = center.Y + numRadius * sin - textSize.Height / 2;
                    g.DrawString(text, this.Font, Brushes.Black, x, y);
                }

                // 用网络时间绘制指针
                DrawHand(g, center, radius * 0.5f, now.Hour % 12 * 30 + now.Minute * 0.5f, 4);
                DrawHand(g, center, radius * 0.7f, now.Minute * 6, 3);
                DrawHand(g, center, radius * 0.9f, now.Second * 6, 1, Color.Red);
                g.FillEllipse(Brushes.Black, center.X - 4, center.Y - 4, 8, 8);
            }

            pbClock.Image = bmp;
        }

        private void DrawHand(Graphics g, Point center, float length, float angleDegrees, int width, Color? color = null)
        {
            float angleRad = (float)((angleDegrees - 90) * Math.PI / 180); // -90°让 12 点朝上
            float x = center.X + length * (float)Math.Cos(angleRad);
            float y = center.Y + length * (float)Math.Sin(angleRad);
            g.DrawLine(new Pen(color ?? Color.Black, width), center.X, center.Y, x, y);
        }

    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NineA {
    private JFrame frame;
    private JButton prevMonthButton;
    private JButton nextMonthButton;
    private JLabel monthYearLabel;
    private JPanel calendarPanel;
    private YearMonth currentYearMonth;
    private Map<LocalDate, List<String>> scheduleMap; // 用于存储日程
    private JTextField dateInputField;
    private JButton goToDateButton;

    public NineA() {
        currentYearMonth = YearMonth.now();
        scheduleMap = new HashMap<>();

        // 初始化界面
        frame = new JFrame("万年历");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // 创建面板
        JPanel contentPanel = new JPanel(new BorderLayout());
        frame.add(contentPanel);

        // 创建按钮
        prevMonthButton = new JButton("<");
        nextMonthButton = new JButton(">");
        monthYearLabel = new JLabel(currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy年MM月")));
        JPanel monthControlPanel = new JPanel();
        monthControlPanel.add(prevMonthButton);
        monthControlPanel.add(monthYearLabel);
        monthControlPanel.add(nextMonthButton);
        contentPanel.add(monthControlPanel, BorderLayout.NORTH);

        // 创建日期跳转面板
        dateInputField = new JTextField("yyyy-MM-dd");
        goToDateButton = new JButton("跳转");
        JPanel dateControlPanel = new JPanel();
        dateControlPanel.add(dateInputField);
        dateControlPanel.add(goToDateButton);
        contentPanel.add(dateControlPanel, BorderLayout.SOUTH);

        // 创建日历面板
        calendarPanel = new JPanel(new GridLayout(0, 7));
        contentPanel.add(calendarPanel, BorderLayout.CENTER);

        // 添加事件监听
        prevMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentYearMonth = currentYearMonth.minusMonths(1);
                monthYearLabel.setText(currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy年MM月")));
                refreshCalendar();
            }
        });

        nextMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentYearMonth = currentYearMonth.plusMonths(1);
                monthYearLabel.setText(currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy年MM月")));
                refreshCalendar();
            }
        });

        // 添加日期跳转事件监听
        goToDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dateText = dateInputField.getText();
                try {
                    LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    currentYearMonth = YearMonth.from(date);
                    monthYearLabel.setText(currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy年MM月")));
                    refreshCalendar();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "日期格式不正确，请使用 yyyy-MM-dd 格式", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 显示日历
        refreshCalendar();
        frame.setVisible(true);
    }

    private void refreshCalendar() {
        calendarPanel.removeAll();
        DayOfWeek firstDayOfMonth = currentYearMonth.atDay(1).getDayOfWeek();
        int daysInMonth = currentYearMonth.lengthOfMonth();

        // 添加星期几的标签
        String[] daysOfWeek = {"日", "一", "二", "三", "四", "五", "六"};
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            calendarPanel.add(label);
        }

        // 添加空的格子，直到第一个星期几
        for (int i = DayOfWeek.MONDAY.getValue(); i < firstDayOfMonth.getValue(); i++) {
            calendarPanel.add(new JLabel());
        }

        // 添加日期的按钮
        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LocalDate date = currentYearMonth.atDay(Integer.parseInt(((JButton) e.getSource()).getText()));
                    // 打开一个对话框来显示或设置日程
                    showScheduleDialog(date);
                }
            });
            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void showScheduleDialog(LocalDate date) {
        // 创建日程对话框
        JDialog dialog = new JDialog(frame, "日程安排", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);

        // 显示现有的日程
        List<String> schedules = scheduleMap.getOrDefault(date, new ArrayList<>());
        JList<String> scheduleList = new JList<>(schedules.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(scheduleList);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // 添加输入框来添加新的日程
        JTextField scheduleField = new JTextField();
        scheduleField.setSize(20, 30);
        JButton addScheduleButton = new JButton("添加");
        JPanel inputPanel = new JPanel();
        inputPanel.setSize(20, 30);
        inputPanel.add(scheduleField);
        inputPanel.add(addScheduleButton);
        dialog.add(inputPanel, BorderLayout.SOUTH);

        // 添加事件监听来处理添加日程
        addScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newSchedule = scheduleField.getText();
                if (!newSchedule.isEmpty()) {
                    schedules.add(newSchedule);
                    scheduleMap.put(date, schedules);
                    scheduleList.setListData(schedules.toArray(new String[0]));
                    scheduleField.setText("");
                }
            }
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NineA();
            }
        });
    }
}

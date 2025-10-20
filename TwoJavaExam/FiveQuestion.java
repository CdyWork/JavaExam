package TwoJavaExam;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class FiveQuestion {

    // 用户数据库
    private Map<String, String> userDatabase = new HashMap<>();

    // 界面组件
    private JFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField electricityUsageField;
    private JLabel resultLabel;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    // 构造方法
    public FiveQuestion() {
        // 默认匿名用户
        userDatabase.put("anonymous", "anonymous");
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("阶梯电费计算器");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(420, 300);
        mainFrame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("登录", buildLoginPanel());
        tabbedPane.addTab("电费计算", buildCalculationPanel());
        tabbedPane.addTab("用户管理", buildUserManagementPanel());

        mainFrame.getContentPane().add(tabbedPane);
        mainFrame.setVisible(true);
    }

    // 登录面板
    private JPanel buildLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("用户名："), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(12);
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("密码："), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(12);
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        return panel;
    }

    // 电费计算面板
    private JPanel buildCalculationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("请输入用电量（度）："), gbc);

        gbc.gridx = 1;
        electricityUsageField = new JTextField(10);
        panel.add(electricityUsageField, gbc);

        JButton calculateButton = new JButton("计算电费");
        calculateButton.addActionListener(e -> handleCalculation());
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        resultLabel = new JLabel("结果：");
        gbc.gridy = 2;
        panel.add(resultLabel, gbc);

        return panel;
    }

    // 用户管理面板
    private JPanel buildUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();

        JButton importButton = new JButton("从 TXT 导入用户");
        JButton exportButton = new JButton("导出用户到 TXT");

        importButton.addActionListener(e -> importUsers());
        exportButton.addActionListener(e -> exportUsers());

        topPanel.add(importButton);
        topPanel.add(exportButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JTextArea infoArea = new JTextArea(8, 30);
        infoArea.setEditable(false);
        updateUserInfo(infoArea);

        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        panel.putClientProperty("infoArea", infoArea);
        return panel;
    }

    private void updateUserInfo(JTextArea infoArea) {
        StringBuilder sb = new StringBuilder("当前用户列表:\n");
        for (String username : userDatabase.keySet()) {
            sb.append(username).append("\n");
        }
        infoArea.setText(sb.toString());
    }

    // 登录逻辑
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String storedPassword = userDatabase.get(username);
        if (storedPassword != null && storedPassword.equals(password)) {
            JOptionPane.showMessageDialog(mainFrame, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 电费计算逻辑
    private void handleCalculation() {
        String input = electricityUsageField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "请输入用电量", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double usage = Double.parseDouble(input);
            if (usage < 0) throw new NumberFormatException();

            double cost = calculateTieredCost(usage);
            resultLabel.setText("结果：" + decimalFormat.format(cost) + " 元");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "请输入有效的数值（非负数）", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 阶梯电价计算
    private double calculateTieredCost(double usage) {
        double cost;
        if (usage <= 240) {
            cost = usage * 0.55;
        } else if (usage <= 540) {
            cost = 240 * 0.55 + (usage - 240) * 0.70;
        } else {
            cost = 240 * 0.55 + (540 - 240) * 0.70 + (usage - 540) * 0.95;
        }
        return cost;
    }

    // 导入用户
    private void importUsers() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                for (String line : Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)) {
                    String trimmed = line.trim();
                    if (trimmed.isEmpty()) continue;
                    String[] parts = trimmed.split(",");
                    if (parts.length >= 2) {
                        userDatabase.put(parts[0].trim(), parts[1].trim());
                    }
                }
                JOptionPane.showMessageDialog(mainFrame, "导入完成", "提示", JOptionPane.INFORMATION_MESSAGE);
                refreshUserPanel();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainFrame, "导入失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 导出用户
    private void exportUsers() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
                for (Map.Entry<String, String> entry : userDatabase.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue());
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(mainFrame, "导出完成", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainFrame, "导出失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 刷新用户面板
    private void refreshUserPanel() {
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                JFrame frame = (JFrame) window;
                for (Component component : frame.getContentPane().getComponents()) {
                    if (component instanceof JTabbedPane) {
                        JTabbedPane tabbedPane = (JTabbedPane) component;
                        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                            Component comp = tabbedPane.getComponentAt(i);
                            if (comp instanceof JPanel) {
                                JPanel panel = (JPanel) comp;
                                JTextArea infoArea = (JTextArea) panel.getClientProperty("infoArea");
                                if (infoArea != null) updateUserInfo(infoArea);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new FiveQuestion();
    }
}

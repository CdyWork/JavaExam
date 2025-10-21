import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import javax.swing.*;

// 定义接口 ICommon
interface ICommon {
    double calculateSpeed(int A, int B, int C);
    double calculateTime(int A, int B, int C);
}

// 实现 Car 类
class Car implements ICommon {
    @Override
    public double calculateSpeed(int A, int B, int C) {
        return (double) A * B / C;
    }

    @Override
    public double calculateTime(int A, int B, int C) {
        return 1000 / calculateSpeed(A, B, C);
    }
}

// 实现 Plane 类
class Plane implements ICommon {
    @Override
    public double calculateSpeed(int A, int B, int C) {
        return A + B + C;
    }

    @Override
    public double calculateTime(int A, int B, int C) {
        return 1000 / calculateSpeed(A, B, C);
    }
}

// 使用反射的 SevenB 类
public class SevenB {
    public static void main(String[] args) {
        // 创建GUI窗口
        JFrame frame = new JFrame("计算交通工具运行时间");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLayout(new GridBagLayout());

        // 创建 GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件之间的间距
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        gbc.anchor = GridBagConstraints.WEST; // 组件靠左对齐

        // 添加标签和输入框
        JLabel vehicleLabel = new JLabel("交通工具类型:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(vehicleLabel, gbc);

        JTextField vehicleField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(vehicleField, gbc);

        JLabel ALabel = new JLabel("A:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(ALabel, gbc);

        JTextField AField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(AField, gbc);

        JLabel BLabel = new JLabel("B:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(BLabel, gbc);

        JTextField BField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(BField, gbc);

        JLabel CLabel = new JLabel("C:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(CLabel, gbc);

        JTextField CField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(CField, gbc);

        JLabel resultLabel = new JLabel("运行时间:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(resultLabel, gbc);

        JTextField resultField = new JTextField();
        resultField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(resultField, gbc);

        // 添加按钮
        JButton calculateButton = new JButton("计算");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // 占据两列
        gbc.anchor = GridBagConstraints.CENTER; // 按钮居中对齐
        frame.add(calculateButton, gbc);

        // 添加文本区域来显示反射特性和好处
        JTextArea reflectionInfo = new JTextArea();
        reflectionInfo.setEditable(false);
        reflectionInfo.setLineWrap(true);
        reflectionInfo.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reflectionInfo);

        String infoText = "Java反射的特性和好处:\n"
                         + "1. 动态加载类：在运行时动态加载类，无需在编译时知道具体类名。\n"
                         + "2. 运行时检查类信息：可以检查类的构造方法、方法、字段等信息。\n"
                         + "3. 动态调用方法：可以在运行时调用类的方法，包括私有方法。\n"
                         + "4. 灵活性：可以灵活地扩展和修改代码，无需修改现有代码。\n"
                         + "5. 可维护性：_future_ 如果增加新的交通工具类型，只需编写新的类并实现 `ICommon` 接口即可。不需要修改现有的 `SevenB` 类。\n";

        reflectionInfo.setText(infoText);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // 占据两列
        gbc.fill = GridBagConstraints.BOTH; // 填充整个区域
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        frame.add(scrollPane, gbc);

        // 按钮点击事件处理
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vehicleType = vehicleField.getText();
                try {
                    int A = Integer.parseInt(AField.getText());
                    int B = Integer.parseInt(BField.getText());
                    int C = Integer.parseInt(CField.getText());

                    // 使用反射创建实例
                    Class<?> clazz = Class.forName(vehicleType);
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    ICommon vehicle = (ICommon) constructor.newInstance();

                    // 计算并显示时间，保留两位小数
                    double time = vehicle.calculateTime(A, B, C);
                    resultField.setText(String.format("%.2f 秒", time));
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "找不到指定的交通工具类型: " + vehicleType, "错误", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "请输入有效的整数", "错误", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "创建交通工具实例时出错", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 显示窗口
        frame.setVisible(true);
    }
}

package TwoJavaExam;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class FourQuestion extends Applet implements ActionListener {
    private TextField inputField;
    private Choice typeChoice;
    private Label resultLabel;
    private Button convertButton;
    private TextArea historyArea;
    private String historyText = ""; 

    public void init() {
        setLayout(new FlowLayout());
        add(new Label("请输入温度："));
        inputField = new TextField(10);
        add(inputField);

        typeChoice = new Choice();
        typeChoice.add("摄氏转华氏");
        typeChoice.add("华氏转摄氏");
        add(typeChoice);

        convertButton = new Button("转换");
        convertButton.addActionListener(this);
        add(convertButton);

        resultLabel = new Label("结果将在此显示");
        add(resultLabel);

        add(new Label("历史记录："));
        historyArea = new TextArea(5, 30);
        historyArea.setEditable(false);
        add(historyArea);
    }

    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText().trim();
        if (input.equals("")) {
            resultLabel.setText("请输入温度数值！");
            return;
        }

        try {
            double value = Double.parseDouble(input);
            double resultValue;
            String result;

            if (typeChoice.getSelectedIndex() == 0) {
                resultValue = value * 9 / 5 + 32;
                result = value + " 摄氏度 = " + String.format("%.2f", resultValue) + " 华氏度";
            } else {
                resultValue = (value - 32) * 5 / 9;
                result = value + " 华氏度 = " + String.format("%.2f", resultValue) + " 摄氏度";
            }

            resultLabel.setText(result);

            historyText += result + "\n";
            historyArea.setText(historyText);

        } catch (NumberFormatException ex) {
            resultLabel.setText("输入格式错误，请输入有效数字！");
        }
    }
}

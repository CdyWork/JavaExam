/**
 * 实验2：判断某年某月的天数
 */
package ThreeJavaExam;

import java.util.Scanner;

public class Two {
    private int year;
    private int month;
    
    /**
     * 构造函数
     */
    public Two(int year, int month) {
        this.year = year;
        this.month = month;
    }
    
    /**
     * 判断是否为闰年
     */
    public boolean isLeapYear() {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    
    /**
     * 优化一下提前使用数组存储天数
     */
    public int getDaysByArray() {
        if (month < 1 || month > 12) {
            return -1;
        }
        
        // 使用数组存储每月天数（索引0不使用，方便对应月份）
        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        // 如果是2月且为闰年，天数为29
        if (month == 2 && isLeapYear()) {
            return 29;
        }
        
        return daysInMonth[month];
    }
    
    /**
     * 验证月份是否合法
     */
    public boolean isValidMonth() {
        return month >= 1 && month <= 12;
    }
    
    /**
     * 获取月份名称
     */
    public String getMonthName() {
        String[] monthNames = {"", "一", "二", "三", "四", "五", "六", 
                               "七", "八", "九", "十", "十一", "十二"};
        return isValidMonth() ? monthNames[month] : "无效";
    }
    
    /**
     * 打印详细信息
     */
    public void printDetails() {
        if (!isValidMonth()) {
            System.out.println("错误：月份不合法！");
            return;
        }
        
        System.out.println("\n===== 日期信息 =====");
        System.out.println("年份：" + year);
        System.out.println("月份：" + getMonthName() + "月 (" + month + "月)");
        System.out.println("是否闰年：" + (isLeapYear() ? "是" : "否"));
        System.out.println("天数：" + getDaysByArray() + "天");
        System.out.println("===================\n");
    }
    
    // Getter和Setter方法
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public void setYear(int year) { this.year = year; }
    public void setMonth(int month) { this.month = month; }
    
    /**
     * 主程序入口
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===== 实验2：日期计算器 =====");
        System.out.print("请输入年份: ");
        int year = scanner.nextInt();
        
        System.out.print("请输入月份(1-12): ");
        int month = scanner.nextInt();
        
        // 创建日期计算器对象
        Two calculator = new Two(year, month);
        
        // 显示详细信息
        calculator.printDetails();
        
        scanner.close();
    }
}
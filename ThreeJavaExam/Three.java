/**
 * 实验3：水仙花数查找器
 */
package ThreeJavaExam;

import java.util.ArrayList;
import java.util.List;

public class Three {
    private int start;      // 搜索范围起始
    private int end;        // 搜索范围结束
    private List<Integer> results;  // 存储找到的水仙花数
    
    /**
     * 构造函数
     */
    public Three(int start, int end) {
        this.start = start;
        this.end = end;
        this.results = new ArrayList<>();
    }
    
    /**
     * 查找范围内所有水仙花数
     */
    public void findAllOptimized() {
        results.clear();
        
        // 针对100-999的优化：直接遍历各位数字
        for (int bai = 1; bai <= 9; bai++) {
            for (int shi = 0; shi <= 9; shi++) {
                for (int ge = 0; ge <= 9; ge++) {
                    int num = bai * 100 + shi * 10 + ge;
                    if (num >= start && num <= end) {
                        int sum = bai * bai * bai + shi * shi * shi + ge * ge * ge;
                        if (sum == num) {
                            results.add(num);
                        }
                    }
                }
            }
        } 
    }
    
    /**
     * 获取数字的各位分解
     */
    public int[] getDigits(int num) {
        int ge = num % 10;
        int shi = (num / 10) % 10;
        int bai = num / 100;
        return new int[]{bai, shi, ge};
    }
    
    /**
     * 打印结果
     */
    public void printResults() {
        if (results.isEmpty()) {
            System.out.println(start + "-" + end + "之间没有水仙花数");
            return;
        }
        
        System.out.println(start + "-" + end + "之间的水仙花数有：");
        for (int num : results) {
            int[] digits = getDigits(num);
            System.out.printf("%d = %d³ + %d³ + %d³%n", 
                            num, digits[0], digits[1], digits[2]);
        }
        System.out.println("共找到 " + results.size() + " 个水仙花数\n");
    }

    public static void main(String[] args) {
        // 功能测试
        Three finder = new Three(100, 999);
        finder.findAllOptimized();
        finder.printResults();

    }
}
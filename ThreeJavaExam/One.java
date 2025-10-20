package ThreeJavaExam;

public class One {
    private int start;      // 起始范围
    private int end;        // 结束范围
    private int divisor;    // 除数
    private int sum;        // 计算结果
    
    public One(int start, int end, int divisor) {
        this.start = start;
        this.end = end;
        this.divisor = divisor;
        this.sum = 0;
    }
    
    public void calculateByFormula() {
        // 找到范围内第一个和最后一个倍数
        int firstMultiple = (start % divisor == 0) ? start : start + (divisor - start % divisor);
        int lastMultiple = end - (end % divisor);
        
        // 如果没有倍数
        if (firstMultiple > end) {
            sum = 0;
            return;
        }
        int count = (lastMultiple - firstMultiple) / divisor + 1;
        sum = count * (firstMultiple + lastMultiple) / 2;
    }

    public void printResult(String method) {
        System.out.println(method + "：");
        System.out.println(start + "-" + end + "之间所有" + divisor + "的倍数之和为: " + sum);
    }

    public static void main(String[] args) {
        One calculator = new One(1, 2000, 3);
        calculator.calculateByFormula();
        calculator.printResult("通过等差数列求和：");
    }
}
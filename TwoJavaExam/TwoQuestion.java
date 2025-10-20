package TwoJavaExam;

public class TwoQuestion {
    private int start;
    private int end;
    
    public TwoQuestion(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean IsPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public int SumPrimes() {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            if (IsPrime(i)) {
                sum += i;
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        // 通过构造函数输入范围
        TwoQuestion pc = new TwoQuestion(1, 1000);
        int sum = pc.SumPrimes();
        System.out.println("1到1000之间所有质数的和为：" + sum);
    }
}

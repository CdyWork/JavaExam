package ThreeJavaExam;

public class Seven {

    public static long fibMemo(int n, long[] memo) {
        if (n <= 2) return 1L;            // 第1、2个月为1对
        if (memo[n] != 0) return memo[n];
        memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
        return memo[n];
    }

    public static void main(String[] args) {
        int months = 24; //24 个月

        long[] memo = new long[months + 1];
        long pairs = fibMemo(months, memo);         // 兔子对数
        long rabbits = pairs * 2;                   // 兔子只数

        System.out.println("经过 " + months + " 个月（2 年）后：");
        System.out.println("兔子对数（pairs） = " + pairs);
        System.out.println("兔子只数（individual rabbits） = " + rabbits);
    }
}

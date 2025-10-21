public class SixA {

    // 两数相加
    public int plus(int a, int b) {
        return a + b;
    }

    // 三数相加
    public int plus(int a, int b, int c) {
        return a + b + c;
    }

    // 四数相加
    public int plus(int a, int b, int c, int d) {
        return a + b + c + d;
    }

    public static void main(String[] args) {
        SixA SixA = new SixA();

        // 测试两数相加
        int sumTwo = SixA.plus(2, 3);
        System.out.println("两数相加的结果: " + sumTwo);

        // 测试三数相加
        int sumThree = SixA.plus(2, 3, 5);
        System.out.println("三数相加的结果: " + sumThree);

        // 测试四数相加
        int sumFour = SixA.plus(2, 3, 5, 7);
        System.out.println("四数相加的结果: " + sumFour);
    }
}

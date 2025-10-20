package ThreeJavaExam;

import java.util.Scanner;

public class Enight {

    /**
     * 递归实现汉诺塔
     */
    public static void hanoi(int n, char from, char aux, char to) {
        if (n == 1) {
            System.out.println("移动盘子 1 ：" + from + " -> " + to);
        } else {
            //先将上面 n-1 个盘子从 A 移到 B
            hanoi(n - 1, from, to, aux);
            //再将最大的盘子从 A 移到 C
            System.out.println("移动盘子 " + n + " ：" + from + " -> " + to);
            //最后将 n-1 个盘子从 B 移到 C
            hanoi(n - 1, aux, from, to);
        }
    }

    /**
     * 主函数：读取输入参数并调用递归函数
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入汉诺塔的盘子数量 n：");
        int n = scanner.nextInt();

        System.out.println("\n汉诺塔移动步骤如下：");
        hanoi(n, 'A', 'B', 'C');

        // 总步数公式：2^n - 1
        long steps = (long) Math.pow(2, n) - 1;
        System.out.println("\n总共需要移动 " + steps + " 步。");

        scanner.close();
    }
}

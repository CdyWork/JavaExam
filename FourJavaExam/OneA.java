import java.util.*;
public class OneA {

    public static void main(String[] args) {
        IntArray arr = new IntArray(10);
        arr.generateRandom(100);  
        arr.print("【生成的原始数组】");

        arr.analyze();
        arr.sort(); 
        arr.print("【排序后的数组】");
    }
}

class IntArray {
    private int[] data;

    public IntArray(int size) {
        data = new int[size];
    }

    //生成随机数组
    public void generateRandom(int bound) {
        Random rand = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt(bound);
        }
    }

    //输出数组
    public void print(String title) {
        System.out.println(title);
        System.out.println(Arrays.toString(data) + "\n");
    }

    //求最大值、最小值、平均值等 
    public void analyze() {
        int max = data[0], min = data[0];
        int sum = 0, maxIndex = 0, minIndex = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
                maxIndex = i;
            }
            if (data[i] < min) {
                min = data[i];
                minIndex = i;
            }
            sum += data[i];
        }

        System.out.println("数组统计结果：");
        System.out.println("最大值: " + max + "，索引: " + maxIndex);
        System.out.println("最小值: " + min + "，索引: " + minIndex);
        System.out.println("总和: " + sum);
        System.out.println("平均值: " + (double) sum / data.length + "\n");
    }

    //排序 
    public void sort() {
        Arrays.sort(data);
    }
}

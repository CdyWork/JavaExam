import java.util.*;
public class TwoA {
    public static void main(String[] args) {
        ArrayDemo demo = new ArrayDemo(); 
        demo.showArrayCopy();             
        demo.showCopyOf();              
        demo.show2DArray();             
        demo.show3DArray();              
    }
}
class ArrayDemo {

    /** 使用 System.arraycopy 拷贝 */
    public void showArrayCopy() {
        System.out.println("【arraycopy 拷贝数组】");

        int[] src = {10, 20, 30, 40, 50};
        int[] dst = new int[src.length];
        System.arraycopy(src, 0, dst, 0, src.length);
        print("源数组", src);
        print("拷贝后", dst);

        int[] part = new int[3];
        System.arraycopy(src, 1, part, 0, 3);
        print("部分拷贝", part);
        System.out.println();
    }

    /** 使用 Arrays.copyOf 拷贝 */
    public void showCopyOf() {
        System.out.println("【copyOf 拷贝数组】");

        int[] src = {10, 20, 30, 40, 50};
        print("源数组", src);

        int[] copy = Arrays.copyOf(src, src.length);
        print("完整拷贝", copy);

        int[] extend = Arrays.copyOf(src, 8);
        print("扩展拷贝", extend);
        System.out.println();
    }

    /** foreach 遍历二维数组 */
    public void show2DArray() {
        System.out.println("【二维数组遍历】");

        int[][] arr2D = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        for (int[] row : arr2D)
            System.out.println(Arrays.toString(row));
        System.out.println();
    }

    /** foreach 遍历三维数组 */
    public void show3DArray() {
        System.out.println("【三维数组遍历】");

        int[][][] arr3D = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{9, 10}, {11, 12}}
        };

        for (int i = 0; i < arr3D.length; i++) {
            System.out.println("第" + (i + 1) + "层:");
            for (int[] row : arr3D[i])
                System.out.println("  " + Arrays.toString(row));
        }
        System.out.println();
    }

    /** 打印数组 */
    private void print(String title, int[] arr) {
        System.out.println(title + ": " + Arrays.toString(arr));
    }
}

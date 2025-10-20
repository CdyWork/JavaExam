import java.util.*;
import java.util.stream.Collectors;

/**
 * 实验1：数组基本操作 - 高级数据结构对比版本
 * 功能：
 * 1) 使用数组、List、Set等多种数据结构实现相同功能
 * 2) 对比输出结果和性能
 */
public class OneUpgrade {
    
    public static void main(String[] args) {
        System.out.println("========== 实验1：数组基本操作 - 数据结构对比 ==========\n");
        
        // 生成共享的随机数据
        List<Integer> originalData = generateRandomData(10);
        
        // 使用不同的数据结构
        arrayBasicOperation(originalData);
        System.out.println("\n" + repeatString("=", 60) + "\n");
        
        listBasicOperation(originalData);
        System.out.println("\n" + repeatString("=", 60) + "\n");
        
        setBasicOperation(originalData);
        System.out.println("\n" + repeatString("=", 60) + "\n");
        
        streamBasicOperation(originalData);
    }
    
    /**
     * 生成随机数据
     */
    public static List<Integer> generateRandomData(int size) {
        List<Integer> data = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            data.add(random.nextInt(100));
        }
        return data;
    }
    
    /**
     * 字符串重复方法（兼容Java 11以下版本）
     */
    public static String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * 方式1：传统数组实现
     */
    public static void arrayBasicOperation(List<Integer> data) {
        System.out.println("【方式1：传统数组（int[]）】");
        
        int[] arr = data.stream().mapToInt(Integer::intValue).toArray();
        
        System.out.print("原始数组: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // 求最大值、最小值、总和
        int max = arr[0], min = arr[0], sum = 0;
        int maxIndex = 0, minIndex = 0;
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                maxIndex = i;
            }
            if (arr[i] < min) {
                min = arr[i];
                minIndex = i;
            }
            sum += arr[i];
        }
        
        System.out.println("最大值: " + max + " (索引: " + maxIndex + ")");
        System.out.println("最小值: " + min + " (索引: " + minIndex + ")");
        System.out.println("总和: " + sum);
        System.out.println("平均值: " + String.format("%.2f", (double)sum / arr.length));
        
        Arrays.sort(arr);
        System.out.print("排序后: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    /**
     * 方式2：List集合实现
     */
    public static void listBasicOperation(List<Integer> data) {
        System.out.println("【方式2：List集合（ArrayList）】");
        
        List<Integer> list = new ArrayList<>(data);
        
        System.out.print("原始列表: ");
        System.out.println(list);
        
        // 求最大值、最小值、总和
        int max = Collections.max(list);
        int min = Collections.min(list);
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        int maxIndex = list.indexOf(max);
        int minIndex = list.indexOf(min);
        
        System.out.println("最大值: " + max + " (索引: " + maxIndex + ")");
        System.out.println("最小值: " + min + " (索引: " + minIndex + ")");
        System.out.println("总和: " + sum);
        System.out.println("平均值: " + String.format("%.2f", (double)sum / list.size()));
        
        Collections.sort(list);
        System.out.print("排序后: ");
        System.out.println(list);
    }
    
    /**
     * 方式3：Set集合实现（自动去重）
     */
    public static void setBasicOperation(List<Integer> data) {
        System.out.println("【方式3：Set集合（TreeSet - 自动排序）】");
        
        Set<Integer> set = new TreeSet<>(data);
        
        System.out.print("原始集合: ");
        System.out.println(set);
        System.out.println("元素个数: " + set.size() + " (已去重)");
        
        // 求最大值、最小值、总和
        int max = set.stream().max(Integer::compareTo).orElse(0);
        int min = set.stream().min(Integer::compareTo).orElse(0);
        int sum = set.stream().mapToInt(Integer::intValue).sum();
        
        System.out.println("最大值: " + max);
        System.out.println("最小值: " + min);
        System.out.println("总和: " + sum);
        System.out.println("平均值: " + String.format("%.2f", (double)sum / set.size()));
        
        System.out.print("排序后: ");
        System.out.println(set);
    }
    
    /**
     * 方式4：Stream流式处理实现
     */
    public static void streamBasicOperation(List<Integer> data) {
        System.out.println("【方式4：Stream流式处理（函数式编程）】");
        
        System.out.print("原始数据: ");
        System.out.println(data);
        
        // 使用Stream进行各种操作
        int max = data.stream().max(Integer::compareTo).orElse(0);
        int min = data.stream().min(Integer::compareTo).orElse(0);
        int sum = data.stream().mapToInt(Integer::intValue).sum();
        double avg = data.stream().mapToInt(Integer::intValue).average().orElse(0);
        
        System.out.println("最大值: " + max);
        System.out.println("最小值: " + min);
        System.out.println("总和: " + sum);
        System.out.println("平均值: " + String.format("%.2f", avg));
        
        // Stream排序和过滤示例
        List<Integer> sorted = data.stream()
                                   .sorted()
                                   .collect(Collectors.toList());
        System.out.print("排序后: ");
        System.out.println(sorted);
        
        // 额外功能：过滤、映射等
        System.out.print("大于50的元素: ");
        data.stream()
            .filter(n -> n > 50)
            .forEach(n -> System.out.print(n + " "));
        System.out.println();
    }
}
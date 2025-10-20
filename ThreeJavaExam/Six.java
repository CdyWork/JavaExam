package ThreeJavaExam;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 实验6：验证整除9的定理
 */
public class Six {  
    // 预计算0-999的数字和
    private static final int[] DIGIT_SUM_CACHE = new int[1000];
    
    static {
        for (int i = 0; i < 1000; i++) {
            int sum = 0;
            int n = i;
            while (n > 0) {
                sum += n % 10;
                n /= 10;
            }
            DIGIT_SUM_CACHE[i] = sum;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 线程数和范围
        final int THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2; // 动态调整
        final long RANGE = (long) Integer.MAX_VALUE;
        final long STEP = RANGE / THREAD_COUNT;

        System.out.println("验证范围: 0 ~ " + RANGE + " (共 " + (RANGE + 1) + " 个数)");
        System.out.println("线程数量: " + THREAD_COUNT + " (CPU核心: " + Runtime.getRuntime().availableProcessors() + ")");

        AtomicBoolean found = new AtomicBoolean(false);
        Thread[] threads = new Thread[THREAD_COUNT];
        long startTime = System.nanoTime();

        for (int t = 0; t < THREAD_COUNT; t++) {
            final int threadId = t;
            final long start = t * STEP;
            final long end = (t == THREAD_COUNT - 1) ? RANGE + 1 : (t + 1) * STEP;  // +1 确保包含最大值

            threads[t] = new Thread(() -> {
                // 核心数学原理：n % 9 == digitSum(n) % 9
                // 所以只需验证这个等式是否总是成立
                for (long n = start; n < end && !found.get(); n++) {
                    int num = (int) n;
                    
                    // 跳过明显符合的情况（9的倍数）
                    int mod9 = num % 9;
                    if (mod9 == 0) continue; // 9的倍数，各位和必定是9的倍数
                    
                    // 快速计算数字和（使用查找表）
                    int sum = fastDigitSum(num);
                    int sumMod9 = sum % 9;
                    
                    // 检查是否违反定理
                    if (sumMod9 == 0 && mod9 != 0) {
                        if (found.compareAndSet(false, true)) {
                            System.out.printf("线程%d发现反例！n=%d 各位和=%d (sum%%9==0, 但 n%%9=%d)%n",
                                    threadId, num, sum, mod9);
                        }
                        return;
                    }
                }
            });
            threads[t].start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }

        long time = System.nanoTime() - startTime;
        System.out.println("----------------------------------");
        if (found.get())
            System.out.println("验证中途终止：发现反例 ❌");
        else
            System.out.println("验证完毕：未发现任何反例 ✅");
        System.out.printf("总用时：%.3f 秒%n", time / 1_000_000_000.0);
    }

    /**
     * 使用预计算表来计算
     */
    private static int fastDigitSum(int n) {
        if (n < 1000) {
            return DIGIT_SUM_CACHE[n];
        }
        
        int sum = 0;
        
        // 分段处理：每次处理3位数字
        while (n >= 1000) {
            int last3 = n % 1000;
            sum += DIGIT_SUM_CACHE[last3];
            n /= 1000;
        }
        
        // 处理剩余部分
        sum += DIGIT_SUM_CACHE[n];
        
        return sum;
    }
}
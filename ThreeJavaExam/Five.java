/**
 * 实验5：百钱买百鸡问题
 */
package ThreeJavaExam;

import java.util.ArrayList;
import java.util.List;


public class Five {
    
    /**
     * 内部类：表示一个购买方案
     */
    public static class PurchasePlan {
        private int hen;        // 母鸡数量
        private int rooster;    // 公鸡数量
        private int chick;      // 小鸡数量
        
        private static final int HEN_PRICE = 5;
        private static final int ROOSTER_PRICE = 3;
        private static final int CHICK_GROUP_PRICE = 1;  // 3只小鸡1分钱
        
        public PurchasePlan(int hen, int rooster, int chick) {
            this.hen = hen;
            this.rooster = rooster;
            this.chick = chick;
        }
        
        public int getHen() { return hen; }
        public int getRooster() { return rooster; }
        public int getChick() { return chick; }
        
        /**
         * 计算总数量
         */
        public int getTotalCount() {
            return hen + rooster + chick;
        }
        
        /**
         * 计算总价格（单位：分）
         */
        public int getTotalPrice() {
            return hen * HEN_PRICE + rooster * ROOSTER_PRICE + chick / 3;
        }
        
        /**
         * 验证方案是否有效
         */
        public boolean isValid() {
            return getTotalCount() == 100 && 
                   getTotalPrice() == 100 && 
                   chick % 3 == 0;
        }

        public String toString() {
            return String.format("母鸡=%2d只, 公鸡=%2d只, 小鸡=%2d只  (总价=%3d分, 总数=%3d只)",
                               hen, rooster, chick, getTotalPrice(), getTotalCount());
        }
    }
    
    private List<PurchasePlan> solutions;
    
    public Five() {
        this.solutions = new ArrayList<>();
    }

    public void solveOneLoop() {
        solutions.clear();
        
        // 只需要枚举母鸡的数量
        for (int hen = 0; hen <= 20; hen++) {
            if ((100 - 7 * hen) % 4 == 0) {
                int rooster = (100 - 7 * hen) / 4;
                int chick = 100 - hen - rooster;
                
                if (rooster >= 0 && chick >= 0) {
                    PurchasePlan plan = new PurchasePlan(hen, rooster, chick);
                    if (plan.isValid()) {
                        solutions.add(plan);
                    }
                }
            }
        }
    }
    
    /**
     * 打印所有解决方案
     */
    public void printSolutions(String methodName) {
        if (solutions.isEmpty()) {
            System.out.println("未找到可行方案！");
            return;
        }
        
        System.out.println(methodName + "：");
        for (int i = 0; i < solutions.size(); i++) {
            System.out.printf("方案%d: %s%n", i + 1, solutions.get(i));
        }
        System.out.println("共有 " + solutions.size() + " 种买法\n");
    }
    
    /**
     * 获取解决方案列表
     */
    public List<PurchasePlan> getSolutions() {
        return new ArrayList<>(solutions);
    }
    
    public static void main(String[] args) {
        System.out.println("===== 实验5：百钱买百鸡问题 =====\n");
        
        Five solver = new Five();

        System.out.println("【方法1：一层循环（最优）】");
        solver.solveOneLoop();
        solver.printSolutions("方法3：一层循环（最优）");  // 调用打印方法显示详细方案
    }
}
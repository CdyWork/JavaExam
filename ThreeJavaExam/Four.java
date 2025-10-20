/**
 * 实验4：数字谜题求解器
 */
package ThreeJavaExam;

import java.util.ArrayList;
import java.util.List;


public class Four {
    
    /**
     * 内部类：表示一个解决方案
     */
    public static class Solution {
        private int x, y, z;
        private int xyz, yzz;
        
        public Solution(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.xyz = x * 100 + y * 10 + z;
            this.yzz = y * 100 + z * 10 + z;
        }
        
        public int getX() { return x; }
        public int getY() { return y; }
        public int getZ() { return z; }
        public int getXYZ() { return xyz; }
        public int getYZZ() { return yzz; }
        public int getSum() { return xyz + yzz; }

        public String toString() {
            return String.format("X=%d, Y=%d, Z=%d  →  %d + %d = %d", 
                               x, y, z, xyz, yzz, getSum());
        }
    }
    
    private int targetSum;
    private List<Solution> solutions;
    
    /**
     * 构造函数
     */
    public Four(int targetSum) {
        this.targetSum = targetSum;
        this.solutions = new ArrayList<>();
    }
    
    /**
     * 方法1：根据个位、十位、百位的进位关系进行求解
     */
    public void solveWithPruning() {
        solutions.clear();
        
        // 分析个位：Z + Z = ?2 (结果个位是2)
        // 可能：Z=1(2), Z=6(12需要进位)
        int[] possibleZ = findPossibleZ();
        
        for (int z : possibleZ) {
            int carry1 = (z + z) / 10;  // 个位进位
            
            // 分析十位：Y + Z + carry1 = ?3 (结果十位是3)
            int[] possibleY = findPossibleY(z, carry1);
            
            for (int y : possibleY) {
                int carry2 = (y + z + carry1) / 10;  // 十位进位
                
                // 分析百位：X + Y + carry2 = 5
                int x = 5 - y - carry2;
                
                if (x >= 1 && x <= 9) {
                    Solution sol = new Solution(x, y, z);
                    if (sol.getSum() == targetSum) {
                        solutions.add(sol);
                    }
                }
            }
        }
    }
    
    private int[] findPossibleZ() {
        List<Integer> possible = new ArrayList<>();
        for (int z = 0; z <= 9; z++) {
            if ((z + z) % 10 == 2) {  // 个位相加后个位是2
                possible.add(z);
            }
        }
        return possible.stream().mapToInt(i -> i).toArray();
    }
    
    private int[] findPossibleY(int z, int carry1) {
        List<Integer> possible = new ArrayList<>();
        for (int y = 1; y <= 9; y++) {
            if ((y + z + carry1) % 10 == 3) {  // 十位相加后十位是3
                possible.add(y);
            }
        }
        return possible.stream().mapToInt(i -> i).toArray();
    }
    
    /**
     * 打印所有解
     */
    public void printSolutions() {
        if (solutions.isEmpty()) {
            System.out.println("未找到解！");
            return;
        }
        
        System.out.println("找到 " + solutions.size() + " 个解：");
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("解" + (i + 1) + ": " + solutions.get(i));
        }
    }
    
    /**
     * 获取解列表
     */
    public List<Solution> getSolutions() {
        return new ArrayList<>(solutions);
    }
    
    public static void main(String[] args) {
        System.out.println("===== 实验4：数字谜题求解器 =====");
        System.out.println("求解：XYZ + YZZ = 532\n");
        
        Four solver = new Four(532);
        
        // 使用优化方法求解
        solver.solveWithPruning();
        solver.printSolutions();
        
        // 验证解的正确性
        System.out.println("===== 解的验证 =====");
        for (Solution sol : solver.getSolutions()) {
            System.out.printf("验证：%d + %d = %d %s%n", 
                            sol.getXYZ(), sol.getYZZ(), sol.getSum(),
                            sol.getSum() == 532 ? "✓" : "✗");
        }
    }
}
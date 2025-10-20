public class FourA {
    // 两个私有属性
    private double width;
    private double height;
    
    // 构造方法，选择width和height作为形式参数
    public FourA(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    // 计算并返回矩形的周长
    public double getPerimeter() {
        return 2 * (width + height);
    }
    
    // 计算并返回矩形的面积
    public double getArea() {
        return width * height;
    }
    
    // main方法用于测试
    public static void main(String[] args) {
        // 创建一个宽度为5，高度为3的矩形
        FourA rect = new FourA(5, 3);
        
        // 计算并打印周长
        System.out.println("周长: " + rect.getPerimeter());  // 输出: 周长: 16.0
        
        // 计算并打印面积
        System.out.println("面积: " + rect.getArea());      // 输出: 面积: 15.0
    }
}
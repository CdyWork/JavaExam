
abstract class Shape {
    // 抽象方法 - 计算面积
    abstract double area();
    
    // 抽象方法 - 打印面积
    abstract void printArea();
}
class Rectangle extends Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    double area() {
        return width * height;
    }
    
    @Override
    void printArea() {
        System.out.println("矩形的面积为: " + area());
    }
}
class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    double area() {
        return Math.PI * radius * radius;
    }
    
    @Override
    void printArea() {
        System.out.println("圆形的面积为: " + area());
    }
}
// 主类
public class OneB {
    public static void main(String[] args) {
        // 创建矩形对象
        Shape rect = new Rectangle(5, 4);
        rect.printArea();
        
        // 创建圆形对象
        Shape circle = new Circle(3);
        circle.printArea();
    }
}
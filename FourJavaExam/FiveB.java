// 定义接口 IShapeArea
interface IShapeArea {
    double getArea();
}

// 抽象类 Shape
abstract class Shape implements IShapeArea {
    protected String name;
    
    public Shape(String name) {
        this.name = name;
    }
    
    // 抽象方法：计算周长
    public abstract double getPerimeter();
    
    // 通用方法：打印信息
    public void printInfo() {
        System.out.println(name + " 周长: " + getPerimeter());
        System.out.println(name + " 面积: " + getArea());
    }
}

// 矩形类
class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super("矩形");
        this.width = width;
        this.height = height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public double getArea() {
        return width * height;
    }

    // 提供公共的 getter 方法
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

// 圆类
class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super("圆形");
        this.radius = radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}

// 正方形类，继承自矩形类
class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
        this.name = "正方形";
    }

    @Override
    public double getPerimeter() {
        return 4 * super.getWidth();
    }

    @Override
    public double getArea() {
        return super.getWidth() * super.getWidth();
    }
}

public class FiveB {
    public static void main(String[] args) {
        // 使用多态处理不同图形（创建对象时类名同步修改）
        Shape[] shapes = {
            new Rectangle(5, 4),
            new Circle(3),
            new Square(4)
        };

        double totalPerimeter = 0;
        double totalArea = 0;

        // 统一处理所有图形
        for (Shape shape : shapes) {
            shape.printInfo();
            totalPerimeter += shape.getPerimeter();
            totalArea += shape.getArea();
            System.out.println();
        }

        System.out.println("所有图形的总周长: " + totalPerimeter);
        System.out.println("所有图形的总面积: " + totalArea);
    }
}

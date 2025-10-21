// 抽象类 Shape
abstract class Shape {
    protected String name;
    
    public Shape(String name) {
        this.name = name;
    }
    
    // 抽象方法：计算周长
    public abstract double getPerimeter();
    
    // 抽象方法：计算面积
    public abstract double getArea();
    
    // 通用方法：打印信息
    public void printInfo() {
        System.out.println(name + " 周长: " + getPerimeter());
        System.out.println(name + " 面积: " + getArea());
    }
}

// 三角形类
class Triangle extends Shape { 
    private double side1;
    private double side2;
    private double side3;
    private boolean isValid;

    public Triangle(double s1, double s2, double s3) { 
        super("三角形");
        this.side1 = s1;
        this.side2 = s2;
        this.side3 = s3;
        this.isValid = checkValidity();
    }

    private boolean checkValidity() {
        return (side1 + side2 > side3) && 
               (side1 + side3 > side2) && 
               (side2 + side3 > side1);
    }

    @Override
    public double getPerimeter() {
        return isValid ? side1 + side2 + side3 : 0;
    }

    @Override
    public double getArea() {
        if (!isValid) return 0;
        double s = getPerimeter() / 2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }

    public boolean isValid() {
        return isValid;
    }

    public void setSides(double s1, double s2, double s3) {
        this.side1 = s1;
        this.side2 = s2;
        this.side3 = s3;
        this.isValid = checkValidity();
    }
}

// 梯形类
class Ladder extends Shape {
    private double upperBase;
    private double lowerBase;
    private double leftBase;
    private double rightBase;
    private double height;

    public Ladder(double upper, double lower, double left, double right, double h) {
        super("梯形");
        this.upperBase = upper;
        this.lowerBase = lower;
        this.height = h;
        this.leftBase = left;
        this.rightBase = right;
    }

    @Override
    public double getPerimeter() {
        return upperBase + lowerBase + leftBase + rightBase;
    }

    @Override
    public double getArea() {
        return (upperBase + lowerBase) * height / 2;
    }
}

// 圆形类
class Circle extends Shape {
    private double radius;

    public Circle(double r) {
        super("圆形");
        this.radius = r;
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

public class FiveA {
    public static void main(String[] args) {
        // 使用多态处理不同图形（创建对象时类名同步修改）
        Shape[] shapes = {
            new Triangle(3, 4, 5), 
            new Ladder(3, 5, 5, 5, 4),
            new Circle(5)
        };

        // 统一处理所有图形
        for (Shape shape : shapes) {
            shape.printInfo();
            if (shape instanceof Triangle) { 
                System.out.println("是否有效三角形: " + ((Triangle)shape).isValid()); 
            }
            System.out.println();
        }
    }
}

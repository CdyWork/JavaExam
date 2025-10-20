// 父类A
class A {
    int sum, num1, num2;
    
    public A() {
        num1 = 10;
        num2 = 20;
        sum = 0;
    }
    
    // 方法sum1 - 计算num1+num2
    void sum1() {
        sum = num1 + num2;
        System.out.println("sum=" + num1 + "+" + num2 + "=" + sum);
    }
    
    // 方法sum2 - 重载方法，接收一个参数n
    void sum2(int n) {
        num1 = n;
        sum = num1 + num2;
        System.out.println("sum=" + num1 + "+" + num2 + "=" + sum);
    }
}

// 子类B - 继承A
class B extends A {
    int num2;  
    
    public B() {
        num2 = 200; 
    }
    
    // sum2方法 - 覆盖父类的sum2方法
    void sum2() {
        sum = num1 + num2;
        System.out.println("sum=" + num1 + "+" + num2 + "=" + sum);
    }
    
    // sum2方法 - 重载方法，接收一个参数n
    void sum2(int n) {
        num1 = n;
        sum = num1 + num2;
        System.out.println("sum=" + num1 + "+" + num2 + "=" + sum);
    }
    
    // sum3方法 - 新增方法，调用父类的sum2方法
    void sum3(int n) {
        super.sum2(n);  // 调用父类的sum2(int n)方法
        System.out.println("sum=" + num1 + "=" + sum);
    }
}

// 测试类
public class TwoB {
    public static void main(String[] arg) {
        B m = new B();
        m.sum1();      // 调用继承自父类A的sum1方法
        m.sum2();      // 调用子类B中新定义的sum2()方法（无参）
        m.sum2(50);    // 调用子类B中的sum2(int n)方法
        m.sum3(50);    // 调用子类B中的sum3(int n)方法
    }
}
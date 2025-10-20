import java.io.*;
// 父类 - SuperClass
class SuperClass {
    int x;
    
    // 父类的构造方法
    SuperClass() {
        x = 3;
        System.out.println("in SuperClass : x=" + x);
    }
    
    // 父类的方法
    void doSomething() {
        System.out.println("in SuperClass.doSomething()");
    }
}

// 子类 - SubClass，继承SuperClass
class SubClass extends SuperClass {
    int x;  
    
    // 子类的构造方法
    SubClass() {
        super();  
        x = 5;   
        System.out.println("in SubClass :x=" + x);
    }
    
    // 子类覆盖父类的方法
    void doSomething() {
        super.doSomething();  
        System.out.println("in SubClass.doSomething()");
        System.out.println("super.x=" + super.x + " sub.x=" + x);
    }
}

public class ThreeB {
    
    public static void main(String[] args) { 
        // 创建SubClass对象
        SubClass subC = new SubClass();
        
        System.out.println();
        
        // 调用对象的方法
        subC.doSomething();

    }
}
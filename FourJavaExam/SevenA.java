// 创建 Person 类
class Person {
    int weight;

    // Person 类的构造方法
    Person() {
        weight = 50;
    }

    void printPerson() {
        System.out.println("Person.printPerson()");
    }
}

// 创建 Person 类的子类 ZhangSan 类
class ZhangSan extends Person {
    int weight;

    // ZhangSan 类的构造方法
    ZhangSan() {
        // 调用 Person 类的构造方法
        super();
        weight = 500;
    }

    // 重写 Person 类的 printPerson() 方法
    void printPerson() {
        System.out.println("ZhangSan.printPerson()");
    }

    // 演示 super 和 this 的用法
    void superThisUseDemo() {
        int weight;
        weight = 5000;
        // 调用 Person 类的 printPerson() 方法
        super.printPerson();
        // 调用 ZhangSan 类的 printPerson() 方法
        printPerson();
        // 显示 Person 类的 weight
        System.out.println("super.weight=" + super.weight);
        // 显示 ZhangSan 类的 weight
        System.out.println("this.weight=" + this.weight);
        System.out.println("weight=" + weight);
    }
}

public class SevenA {
    public static void main(String[] args) {
        ZhangSan zhangsan = new ZhangSan();
        zhangsan.superThisUseDemo();
    }
}
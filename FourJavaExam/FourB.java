public class FourB {
    // 成员变量
    private int instanceVar;

    // 静态成员变量
    private static int staticVar;

    // 修正为实例方法（移除static修饰符），避免静态方法中使用this的错误
    public void method(int paramVar, int instanceVar) {
        // 局部变量
        int localVar = 10;

        // 变量赋值
        this.instanceVar = instanceVar; // 使用this区分成员变量和参数变量
        staticVar = paramVar;

        System.out.println("成员变量：" + this.instanceVar);
        System.out.println("静态变量：" + staticVar);
        System.out.println("参数变量：" + paramVar);
        System.out.println("局部变量：" + localVar);
    }

    public static void main(String[] args) {
        // 创建实例并调用方法
        new FourB().method(20, 30);
    }
}
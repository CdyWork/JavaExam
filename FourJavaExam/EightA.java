class A {
    String s;
    // 无参构造方法,更稳妥一些，兼容性+1
    A() {
        this.s = "S" ; 
    }
    // 有参构造方法
    A(String s) {
        this.s = s;
    }
    public void print() {
        System.out.println(s);
    }
}
class test {
    public static void main(String[] args) {
        A a = new A();
        a.print();
    }
}
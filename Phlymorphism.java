/**
 * 多态Demo
 * 已知类A、B、C、D，B继承于A，C和D继承于B，具体如下
 * 优先级从高到低：this.show(O)、super.show(O)、this.show((super)O)、super.show((super)O)
 */
public class Phlymorphism {
    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new B();
        B b = new B();
        C c = new C();
        D d = new D();

        a1.print(b);//A and A
        a1.print(c);//A and A
        a1.print(d);//A and D

        a2.print(c);//B and A
        a2.print(c);//B and A
        a2.print(d);//A and D

        b.print(b);//B and B
        b.print(c);//B and B
        b.print(d);//A and D

        d.print(b);//B and B
        d.print(c);//B and B
        d.print(d);//D and D
    }
}

class A {
    public void print(A a){
        System.out.println("A and A");
    }

    public void print(D d){
        System.out.println("A and D");
    }
}

class B extends A{
    public void print(B b){
        System.out.println("B and B");
    }

    public void print(A a){
        System.out.println("B and A");
    }
}
class C extends B{
    public void print(C c){
        System.out.println("C and C");
    }

    public void print(A a){
        System.out.println("C and A");
    }
}
class D extends B{
    public void print(D a){
        System.out.println("D and D");
    }

    public void print(A a){
        System.out.println("D and A");
    }
}
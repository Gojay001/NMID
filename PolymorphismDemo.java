/**
 * Created by Gojay on 2017/7/28.
 * 多态理解Demo：
 * 成员变量（编译运行都看左边）、非静态成员函数（编译看左边，运行看右边）、静态成员函数（编译运行都看左边）
 * 子类覆盖父类成员变量：父类方法使用的是父类成员变量，子类方法使用的是子类成员变量。
 * 非静态成员函数：向上转型（子类提升）、向下转型（子类强制转换）、强制转换（编译报错）
 */
public class PolymorphismDemo {
    public static void main(String[] args) {
        //向上转型
        Person a = new User();
        a.show();
        //父类引用变量调用子类特有方法时编译报错
        //System.out.println(a.getName());

        //向下转型
        if (a instanceof User) {
            User b = (User)a;
            b.show();
            System.out.println(b.getName());
        } else if (a instanceof VIP) {
            VIP b = (VIP)a;
            b.show();
            System.out.println(b.getName());
        }

        //强制转换
        //Person c = new Person();
        //强制将父类对象转为子类类型时编译报错
        //VIP d = (VIP)c;

        //访问成员变量
        System.out.println(a.name);
    }
}

/**
 * 父类--Person
 */
class Person {
    protected String name = "Person";

    public void show() {
        System.out.println("父类");
    }
}

/**
 * 子类--User
 */
class User extends Person {
    protected String name = "User";

    @Override
    public void show() {
        System.out.println("子类-普通用户");
    }

    public String getName() {
        return name;
    }
}

/**
 * 子类--VIP
 */
class VIP extends Person {
    protected String name;

    @Override
    public void show() {
        System.out.println("子类-会员用户");
    }

    public String getName() {
        return name;
    }
}

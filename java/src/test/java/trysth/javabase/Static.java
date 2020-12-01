package trysth.javabase;

/**
 * @author hefei
 * @date 2018/1/5
 * 静态块: 类加载的时候执行,且只执行一次; 用于未初始化时,主动加载一些
 * 实例块(instance init block): 每次初始化执行,先于构造方法
 */
public class Static {

    static {
        System.out.println("static block");
    }

    public Static() {
        System.out.println("construct");
    }

    {
        System.out.println("block");
    }

    public static void main(String[] args) {
        new Static();
        new Static();
    }
}

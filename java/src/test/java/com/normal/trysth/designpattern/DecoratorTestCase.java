package com.normal.trysth.designpattern;

/**
 * @author hefei
 * @date 2018/2/6
 * 装饰器模式,也称为wrapper模式,其实就是封装的一种体现.
 * 实现方式: 主要就一个class,这个类实现了一个约定接口,又组合一个这个约定接口
 * 可以在运行时动态的添加功能.一层包一层, 但对于客户端而言,调用透明,都是这个约定接口.
 */
public class DecoratorTestCase {
    public static void main(String[] args) {
        Comp comp = new Component1(new Component2());
        comp.someMethod();
    }
}

interface Comp {
    void someMethod();
}

class Component1 implements Comp {
    private Comp comp;

    public Component1(Comp comp) {
        this.comp = comp;
    }

    @Override
    public void someMethod() {
        //someThing
        System.out.println("component1 something");
        comp.someMethod();
    }
}

class Component2 implements Comp {
    private Comp comp;

    @Override
    public void someMethod() {
        System.out.println("component2 something");
    }
}
package trysth.designpattern;

/**
 * @author hefei
 * @date 2018/1/8
 * 构建者模式一般用来构建一些有相似结构的产品
 * 如kfc套餐,通常包含drink 和 food
 * 如果产品之间差异很大的话,优先是用工厂模式
 *
 * 另外,关于简单工厂模式和工厂模式的说明:
 * 1. 简单工厂模式只是启用一个单独的工厂类来负责构建复杂对象,其实也称不上是一个设计模式
 * 2. 工厂模式是对简单工厂模式的抽象,对产品和工厂类分别提取一个接口出来,适用于这个工厂类
 * 不知道要创建怎样的产品,没有规律可寻的情况,自己用的比较少
 */
public class BuildTestCase {

    interface Builder {
        String drink();

        String food();
    }

    static class PackageA implements Builder {

        @Override
        public String drink() {
            return "可乐";
        }

        @Override
        public String food() {
            return "香辣鸡腿堡";
        }
    }

    static class PackageB implements Builder {

        @Override
        public String drink() {
            return "奶茶";
        }

        @Override
        public String food() {
            return "蛋挞";
        }
    }

    //Director 面向客户端,使得客户端不用关心具体构建过程
    static class Director {
        private Builder builder;

        public Director(Builder builder) {
            this.builder = builder;
        }

        public String getResult() {
            return builder.drink() + "-" + builder.food();
        }
    }

    public static void main(String[] args) {
        Builder packageA = new PackageA();
        Director director = new Director(packageA);
        String result = director.getResult();
    }
}

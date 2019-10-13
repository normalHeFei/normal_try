package com.normal.trysth.javabase;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Created by normalhefei on 2017/3/19.
 */
public class Lambda {
    public static void main(String[] args) {
        //lambda 表达式是否可以理解为函数接口的一个实现实例?
        //(x,y) ->x+y 其实就是一个 "两位操作的"具体实现.
        BinaryOperator<Long> add = (x, y) -> x + y;
        System.out.println(add.apply(1L, 2L));
        //stream:有点像构建者模式有木有?
        List<Integer> list = Arrays.asList(3, 1, 4, 5, 6);
        long count = list.stream()
                .filter(integer -> {
                    System.out.println(integer);
                    return integer > 3;
                    //todo: 惰性求值的实现机制:
                }).count();
        //常见的流操作:
        Integer result = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(result);
        //什么是stream?
        //collection 是面向内存的,而steam 是面向cpu的, 数据结构用来维护规范的内存结构
        //而stream的设计用来实现高效的cpu运算.
        Person.tenPerson()
                .stream()
                //flatMap 一对多个 stream
                .flatMap(person -> person.getStuff1().stream())
                .collect(Collectors.toList())
                .forEach(System.out::print);
        
    }

}

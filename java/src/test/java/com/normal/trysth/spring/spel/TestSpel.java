package com.normal.trysth.spring.spel;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Created by hefei on 2017/8/28.
 * spel: 独立，可以直接用
 * 1. 作用于xml，annotation
 * 2.
 * validator:
 * 1.ValidationUtils
 * 2.error 由 messageCodeResolver解决
 */
public class TestSpel {
    private ExpressionParser parser = new SpelExpressionParser();


    @Test
    public void test() {
        Expression expression = parser.parseExpression("3+4");
        System.out.println(expression.getValue());
        //对象引用
        TestClass testClass = new TestClass("hello spel");
    }

    static class TestClass {
        private String simpleValue;

        public TestClass(String simpleValue) {
            this.simpleValue = simpleValue;
        }

        public String getSimpleValue() {
            return simpleValue;
        }

        public void methodBeRefer(int param) {
            System.out.println(param);
        }
    }
}

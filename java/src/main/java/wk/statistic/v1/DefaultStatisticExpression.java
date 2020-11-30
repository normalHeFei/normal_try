package wk.statistic.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: fei.he
 */
public class DefaultStatisticExpression implements StatisticExpression {

    private String expression;

    private final static List<String> supportOps = Arrays.asList("+", "-", "*", "/");

    private static Pattern expressionItemPattern = Pattern.compile("\\+|\\-|\\*|\\/|\\(|\\)|\\w*");


    @Autowired
    private ApplicationContext applicationContext;

    public DefaultStatisticExpression(String expression) {
        this.expression = expression;
    }


    @Override
    public String getValue(StatisticContext context) {

        boolean notIncludeOp = supportOps.stream().allMatch((op) -> expression.indexOf(op) == -1);

        if (notIncludeOp) {
            StatisticFactor factor = applicationContext.getBean(expression, StatisticFactor.class);
            List<Object> args = context.get(expression);
            return factor.execute(args);
        }

        Matcher matcher = expressionItemPattern.matcher(expression);

        Deque<ExpressionItem> symbols = new ArrayDeque<>();

        Deque<ExpressionItem> factorOrNums = new ArrayDeque<>();

        for (; matcher.find(); ) {

            ExpressionItem item = new ExpressionItem(matcher.group());

            if (item.isFactor()) {
                factorOrNums.push(item);
            }
            if (item.isLeftBracket()) {
                symbols.push(item);
            }

            if (item.isOps()) {
                ExpressionItem headOps = symbols.peek();
                /**
                 * 当前运算符优先级高则入栈
                 */
                if (headOps == null || (headOps != null && item.priorityHigh(headOps))) {
                    symbols.push(item);
                } else {
                    /**
                     * 将操作位栈中的优先级高或相等的全都执行完
                     */
                    for (; !item.priorityHigh(symbols.peek()); ) {
                        calculateMidVal(symbols, factorOrNums, context);
                    }
                }
            }
            /**
             右括号的话, 代表该计算了,直到碰到左括号为止
             */
            if (item.isRightBracket()) {
                for (; !symbols.peek().isLeftBracket(); ) {
                    calculateMidVal(symbols, factorOrNums, context);
                }
            }
        }

        /**
         * 结束时,触发计算,逻辑同之前的
         */
        for (; symbols.peek() != null; ) {
            calculateMidVal(symbols, factorOrNums, context);
        }

        ExpressionItem rst = factorOrNums.peek();

        return rst.item;
    }

    private void calculateMidVal(Deque<ExpressionItem> symbols, Deque<ExpressionItem> factorOrNums, StatisticContext context) {
        ExpressionItem factorR = factorOrNums.pop();
        ExpressionItem factorL = factorOrNums.pop();
        ExpressionItem midVal = calculate(factorL, factorR, symbols.pop(), context);
        factorOrNums.push(midVal);
    }

    private ExpressionItem calculate(ExpressionItem factorL, ExpressionItem factorR, ExpressionItem symbol, StatisticContext context) {
        //todo
        return null;
    }

    @Override
    public <T> T getValue(Class<T> clazz, StatisticContext context) {
        String value = getValue(context);

        if (clazz.equals(String.class)) {
            return clazz.cast(value);
        }
        if (clazz.equals(BigDecimal.class)) {
            return clazz.cast(new BigDecimal(value));
        }
        throw new IllegalArgumentException("除 BigDecimal, String 类型外, 其他计算结果不支持");
    }

    public static void main(String[] args) {
        String input = "ab/cd-ef-gh*ij+(kl-mdo)*oqst";
        Matcher matcher = expressionItemPattern.matcher(input);


    }

    static class ExpressionItem {

        private String item;

        private final static Pattern pattern = Pattern.compile("[A-Za-z]");

        public ExpressionItem(String item) {
            this.item = item;
        }

        public boolean isLeftBracket() {
            return "(".equals(item);
        }

        public boolean isRightBracket() {
            return ")".equals(item);
        }

        public boolean isOps() {
            return supportOps.indexOf(item) != -1;
        }

        public boolean isFactor() {
            Matcher matcher = pattern.matcher(item);
            return matcher.matches();
        }


        public boolean priorityHigh(ExpressionItem headOps) {
            return this.getPriority() < headOps.getPriority();
        }

        private int getPriority() {
            if (item == null) {
                return 2;
            }
            switch (item) {
                case "+":
                    return 2;
                case "-":
                    return 2;
                case "*":
                    return 1;
                case "/":
                    return 1;
                default:
                    return 2;
            }
        }
    }

}

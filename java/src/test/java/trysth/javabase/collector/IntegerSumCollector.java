package trysth.javabase.collector;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * @author hefei
 * @date 2018/5/9
 * ### 要点
 * - 关键还是理解 Collector<T,A,R> 三个泛型类的含义.特别是A

 * T: 容器对象类型
 * A: Map-Reduce 中的Reduce 操作返回的中间类型,通常情况下是中间计算结果的容器对象
 * R: 结果返回类型
 *
 */
public class IntegerSumCollector implements Collector<Integer, IntegerSumCollector.IntegerSum, Integer> {
    static class IntegerSum {
        private Integer sum;

        public IntegerSum(Integer sum) {
            this.sum = sum;
        }

        public void add(Integer i) {
            if (i.intValue() / 2 != 0) {
                sum += i;
            } else {
                sum = sum + (i * 2);
            }
        }

        public IntegerSum merge(IntegerSum iSum) {
            this.add(iSum.sum);
            return this;
        }

        public Integer toValue() {
            return this.sum;
        }
    }

    @Override
    public Supplier<IntegerSum> supplier() {
        return () -> new IntegerSum(0);
    }

    @Override
    public BiConsumer<IntegerSum, Integer> accumulator() {
        return IntegerSum::add;
    }

    @Override
    public BinaryOperator<IntegerSum> combiner() {
        return IntegerSum::merge;
    }

    @Override
    public Function<IntegerSum, Integer> finisher() {
        return IntegerSum::toValue;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }


    /**
     * 出现次数统计
     */
    static class CollectorFunctions {

        public void accumulator(Map<String, Integer> innerRst, String nextStr) {
            if (innerRst.containsKey(nextStr)) {
                int count = Integer.valueOf(String.valueOf(innerRst.get(nextStr)));
                innerRst.put(nextStr, ++count);
            } else {
                innerRst.put(nextStr, 1);
            }
        }

        public Map<String, Integer> merge(Map<String, Integer> beforeRst, Map<String, Integer> afterRst) {
            beforeRst.putAll(afterRst);
            return beforeRst;
        }

        public Map<String, Integer> finish(Map<String, Integer> rst) {
            return rst;
        }
    }


    static class AppearCountCollector implements Collector<String, Map<String, Integer>, Map<String, Integer>> {

        private CollectorFunctions functions;

        public AppearCountCollector(CollectorFunctions functions) {
            this.functions = functions;
        }

        @Override
        public Supplier<Map<String, Integer>> supplier() {
            return () -> new HashMap<>(8);
        }

        @Override
        public BiConsumer<Map<String, Integer>, String> accumulator() {
            return functions::accumulator;
        }

        @Override
        public BinaryOperator<Map<String, Integer>> combiner() {
            return functions::merge;
        }

        @Override
        public Function<Map<String, Integer>, Map<String, Integer>> finisher() {
            return functions::finish;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
        }
    }

    public static void main(String[] args) {
//        Integer sum = Stream.of(1, 2, 3).collect(new IntegerSumCollector());
//        System.out.println(sum);

        Map<String, Integer> collect = Stream.of("a", "b", "b", "b", "c", "c").collect(new AppearCountCollector(new CollectorFunctions()));
        for (Map.Entry<String, Integer> entry : collect.entrySet()) {
            System.out.println("key:" + entry.getKey() + "\nvalue:" + entry.getValue());
        }

    }
}


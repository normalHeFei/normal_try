package com.normal.trysth.javabase.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author hefei
 * @date 2018/5/9
 */
public class TestListCollector implements Collector<String, List<String>, String> {
    @Override
    public Supplier<List<String>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<String>, String> accumulator() {
        return (a, b) -> a.add(b);
    }

    @Override
    public BinaryOperator<List<String>> combiner() {
            return null;
    }

    @Override
    public Function<List<String>, String> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}

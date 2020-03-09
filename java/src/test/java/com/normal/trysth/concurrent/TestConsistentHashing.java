package com.normal.trysth.concurrent;

import java.math.BigDecimal;
import java.util.*;

import static java.util.Objects.hash;


public class TestConsistentHashing {

    public static void main(String[] args) {
        ConsistentHashCircle consistentHashCircle = new ConsistentHashCircle(Arrays.asList(
                new RealNode("192.168.0.1"),
                new RealNode("192.168.0.2"),
                new RealNode("192.168.0.3"),
                new RealNode("192.168.0.4"),
                new RealNode("192.168.0.5")
        ),
                20);
        //do cache
        doCache(consistentHashCircle);
        //print result
        System.out.println("cache info:");
        consistentHashCircle.printHitRateForEveryRealNode();
        //reset
        consistentHashCircle.reset();

        //add node
        consistentHashCircle.addNode(new RealNode("192.168.0.6"));

        System.out.println();
        doCache(consistentHashCircle);
        System.out.println("cache info after add node:");
        consistentHashCircle.printHitRateForEveryRealNode();

    }

    private static void doCache(ConsistentHashCircle consistentHashCircle) {
        for (int i = 0; i < 100000; i++) {
            consistentHashCircle.cache("Cache" + i + "adddddd" + i);
        }
    }

    interface Node {
        void increase();

        int count();

        void reset();
    }

    static class RealNode implements Node {
        private String ip;
        private int count;

        public RealNode(String ip) {
            this.ip = ip;
        }

        @Override
        public String toString() {
            return ip;
        }

        @Override
        public void increase() {
            count++;
        }

        @Override
        public int count() {
            return count;
        }

        @Override
        public void reset() {
            this.count = 0;
        }
    }

    static class VirNode implements Node {
        private int suffix;
        private RealNode realNode;
        private int count;

        public VirNode(RealNode realNode, int suffix) {
            this.realNode = realNode;
            this.suffix = suffix;
        }

        @Override
        public String toString() {
            return this.realNode.ip + ":" + suffix;
        }

        @Override
        public void increase() {
            this.count++;
            realNode.increase();
        }

        @Override
        public int count() {
            return count;
        }

        @Override
        public void reset() {
            this.count = 0;
            realNode.reset();
        }
    }


    static abstract class Circle {

        abstract void addNode(RealNode realNode);

        abstract void deleteNode(RealNode realNode);

        abstract void cache(Object cacheObject);

        abstract void printHitRateForEveryRealNode();

        abstract void reset();
    }

    static class ConsistentHashCircle extends Circle {
        final static long max_node_num = 1L << 32;

        private long totalCount = 0L;

        private TreeMap<Long, Node> innerCircle;

        public ConsistentHashCircle(List<RealNode> realNodes, int virNodeNum) {
            innerCircle = new TreeMap<>();
            //init circle
            for (RealNode realNode : realNodes) {
                long hash = hash(realNode) % max_node_num;
                innerCircle.put(hash, realNode);
                //init vir realNode
                for (int i = 0; i < virNodeNum; i++) {
                    VirNode virNode = new VirNode(realNode, i);
                    long virHash = hash(virNode) % max_node_num;
                    innerCircle.put(virHash, virNode);
                }
            }
        }

        @Override
        void addNode(RealNode realNode) {
            innerCircle.put(Long.valueOf(hash(realNode)), realNode);
        }

        @Override
        void deleteNode(RealNode realNode) {
            innerCircle.remove(realNode);
        }

        @Override
        void cache(Object cacheObject) {
            long hash = hash(cacheObject) % max_node_num;
            totalCount++;

            SortedMap<Long, Node> nextBiggerHashNodeMap = innerCircle.tailMap(hash);
            if (nextBiggerHashNodeMap.isEmpty()) {
                Map.Entry<Long, Node> firstNode = innerCircle.firstEntry();
                firstNode.getValue().increase();
            } else {
                Long firstKey = nextBiggerHashNodeMap.firstKey();
                Node nextNode = nextBiggerHashNodeMap.get(firstKey);
                nextNode.increase();
            }
        }

        @Override
        void printHitRateForEveryRealNode() {
            //print
            for (Node node : innerCircle.values()) {
                if (node instanceof RealNode) {
                    System.out.println("node:" + node + "  count:" + node.count() + " totalCount:" + totalCount + " rate:" + new BigDecimal(node.count()).divide(new BigDecimal(totalCount)));
                }
            }
        }

        @Override
        void reset() {
            for (Node node : innerCircle.values()) {
                node.reset();
            }
            totalCount = 0;
        }
    }
}

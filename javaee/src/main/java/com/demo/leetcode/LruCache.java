package com.demo.leetcode;

import com.concise.component.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 手写lru算法
 * 数据结构: 哈希 + 双向链表
 * @author shenguangyang
 * @date 2022-06-25 14:44
 */
public class LruCache {
    private final int capacity;
    private final Map<Integer, Node<Integer, Integer>> map;
    private final TwoWayLinkedList<Integer, Integer> twoWayLinkedList;

    private static class Node<K, V> {
        Node<K, V> prev;
        Node<K, V> next;
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node() {
        }
    }

    private static class TwoWayLinkedList<K, V> {
        Node<K, V> head;
        Node<K, V> tail;

        public TwoWayLinkedList() {
            this.head = new Node<>();
            this.tail = new Node<>();
            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public void addHead(Node<K, V> node) {
            node.next = this.head.next;
            node.prev = this.head;
            this.head.next.prev = node;
            this.head.next = node;
        }

        public void removeNode(Node<K, V> node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.next = null;
            node.prev = null;
        }

        public Node<K, V> getLast() {
            return this.tail.prev;
        }

        public void printfKey() {
            Node<K, V> nextNode = this.head.next;
            StringBuilder resultStr = new StringBuilder();
            while (nextNode != this.tail) {
                resultStr.append(nextNode.key).append(",");
                nextNode = nextNode.next;
            }
            if (!StringUtils.isEmpty(resultStr)) {
                System.out.println(resultStr.substring(0, resultStr.lastIndexOf(",")));
            }
        }
    }

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.twoWayLinkedList = new TwoWayLinkedList<>();
    }

    public synchronized int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        Node<Integer, Integer> findNode = map.get(key);
        twoWayLinkedList.removeNode(findNode);
        twoWayLinkedList.addHead(findNode);
        return findNode.value;
    }

    public synchronized void put(int key, int value) {
        if (capacity <= map.size()) {
            Node<Integer, Integer> last = twoWayLinkedList.getLast();
            twoWayLinkedList.removeNode(last);
            map.remove(last.key);
        }
        Node<Integer, Integer> newNode = new Node<>(key, value);
        twoWayLinkedList.addHead(newNode);
        map.put(key, newNode);
    }

    public void printfLinkedList() {
        twoWayLinkedList.printfKey();
    }

    public static void main(String[] args) {
        LruCache lruCache = new LruCache(3);
        lruCache.put(1, 2);
        lruCache.put(2, 3);
        lruCache.put(3, 3);
        System.out.println(lruCache.get(2));
        lruCache.put(4, 4);
        lruCache.printfLinkedList();
    }
}

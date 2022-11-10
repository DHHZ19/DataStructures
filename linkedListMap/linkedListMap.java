package edu.ust.cisc;

import java.util.*;

public class CiscTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    CiscEntry<K, V> root;
    private int size;
    private V retValue;

    CiscTreeMap() {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public V get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public V remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putAll(Map m) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    private class CiscEntry<K, V> implements Map.Entry<K, V> {

        K key;
        V value;
        CiscEntry<K, V> left;
        CiscEntry<K, V> right;

        @Override
        public K getKey() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public V getValue() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public V setValue(V value) {
            // TODO Auto-generated method stub
            return null;
        }
    }

    @Override
    public V put(K key, V value) {
        // TODO Auto-generated method stub
        return null;
    }
}

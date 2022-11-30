package edu.ust.cisc;

import java.util.*;

public class CiscTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private CiscEntry<K, V> root;
    private int size;
    private V retValue;
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
        return containsKey(root, (K) key);
    }
    private boolean containsKey(CiscEntry<K, V> node, K key){
        if(node == null){
            return false;
        }else {
            int compare = key.compareTo(node.key);
            if(compare == 0){
                return true;
            }else if(compare < 0){
                return containsKey(node.left, key);
            }else{
                return containsKey(node.right, key);
            }
        }
    }


    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public V get(Object key) {
        return get(root,(K) key);
    }
    private V get(CiscEntry<K, V> node, K key) {
        if(node == null){
            return null;
        }else {
             int compare = ((K) key).compareTo(node.key);
             if(compare == 0){
                 return node.value;
             }else if(compare < 0){
                 return get(node.left, key);
             }else{
                 return get(node.right, key);
             }
        }
    }
    @Override
    public V put(K key, V value) {
        root = put(root, key, value);
        return retValue;
    }
    private CiscEntry<K, V> put(CiscEntry<K, V> node, K key, V value){
        if(node == null){
            node = new CiscEntry<>(key, value);
            this.size++;
            retValue = null;
        }else if(node.key.compareTo(key) > 0){
            node.left = put(node.left, key, value);
        }else if(node.key.compareTo(key) < 0){
            node.right = put(node.right, key, value);
        }else{
            retValue = node.value;
            node.value = value;
        }
        return node;
    }


    @Override
    public V remove(Object key) {
        root = remove(root, (K) key);
        return retValue;
    }

    private CiscEntry<K, V> remove(CiscEntry<K, V> node, Object key){
        if(node ==  null){
            retValue = null;
            return null;
        }else if(node.key.compareTo((K) key) > 0){
            node.left = remove(node.left, key);
        }else if(node.key.compareTo((K)  key) < 0){
            node.right = remove(node.right, key);
        }else{
            if(retValue == null){
                retValue = node.value;
            }
            if(node.right == null){
                size--;
                return node.left;
            }else if( node.right == null){
                size--;
                return node.right;
            }else{
                // both child's replace w/max from L
                CiscEntry entry = getMax(node.left);
                node.key = (K) entry.key;
                node.value = (V) entry.value;
                node.left = remove(node.left, node.key);
            }
        }
        return node;
    }
    private CiscEntry<K, V> getMax(CiscEntry<K, V> node){
        if(node.right == null){
            return node;
        }else {
            return getMax(node.right);
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Entry<? extends K, ? extends V> entry : m.entrySet()){
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        for(Entry<K, V> entry: entrySet()){
            keySet.add(entry.getKey());
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for(Map.Entry<K,V> entry: entrySet()){
            values.add(entry.getValue());
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new CiscEntrySet();
    }

    private static class CiscEntry<K, V> implements Map.Entry<K, V>{
        private K key;
        private V value;
        private CiscEntry<K, V> left;
        private CiscEntry<K, V> right;

        public CiscEntry(K key, V value){
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;

        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
    public class CiscEntrySet extends AbstractSet<Map.Entry<K, V>> {

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return null;
        }

        @Override
        public int size() {
            return size;
        }

        public class CiscEntrySetIterator implements Iterator<Map.Entry<K, V>> {
            private Stack<CiscEntry<K, V>> stack;
            public CiscEntrySetIterator(CiscEntry<K, V> node) {
            }
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Entry<K, V> next() {
                return null;
            }
        }
    }

}

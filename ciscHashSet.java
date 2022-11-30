package edu.ust.cisc;

import java.util.*;

public class CiscHashSet<E> implements CiscCollection<E> {

    private static final double MAX_LOAD_FACTOR = .75;
    private Object[] elementData;
    private int size;
    private final Object REMOVED = new Object();

    public CiscHashSet() {
        elementData = new Object[11];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E value) {
        int bucket = hashFunction(value);
        while(elementData[bucket] != null && !elementData[bucket].equals(value) && ){
            bucket = (bucket + 1) % elementData.length;
        }
//        if(elementData[bucket] == REMOVED){
//            int probingBucket = (bucket + 1) % elementData.length;
//            while(elementData[probingBucket] != null && elementData[probingBucket]){
//                probingBucket = (probingBucket + 1) % elementData.length;
//            }
//            if (elementData[probingBucket] != null && elementData[probingBucket].equals()) {
//                return;
//            }
//        }
    }

    public void remove(E value) {
        int bucket = hashFunction(value);
        Object current = elementData[bucket];

        while(current != null && !current.equals(value)){
            bucket = (bucket + 1) % elementData.length;
            current = elementData[bucket];
        }
        if(current  != null){
            elementData[bucket] = REMOVED;
            size--;
        }
    }

    public int hashFunction(Object value) {
        return Math.abs(value.hashCode()) % elementData.length;
    }

    @Override
    public boolean contains(Object o) {
        int bucket = hashFunction(o);
        Object current = elementData[bucket];

        while (current == REMOVED || (current != null && !current.equals(o))) {
            bucket = (bucket + 1) % elementData.length;
            current = elementData[bucket];
        }
        return current != null;
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscHashSetIterator();
    }

    @Override
    public Object[] toArray() {
       Object[] retArray = new Object[size];
       Iterator<E> itr = iterator();
       int i = 0;
       while(itr.hasNext()){
           retArray[i] = itr.next();
           i++;
       }
       return retArray;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append("[");
        if (!isEmpty()) {
            for (int i = 0; i < elementData.length; i++) {
                if (elementData[i] != null && elementData[i] != REMOVED) {
                    if (!first) {
                        sb.append(", ");
                    }
                    sb.append(elementData[i]);
                    first = false;
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public void clear() {
        for(int i = 0; i < elementData.length; i++){
            elementData[i] = null;
        }
        size = 0;
    }
    public class CiscHashSetIterator implements Iterator<E>{
        private int nextIndex = -1;

        public CiscHashSetIterator(){
            if(!isEmpty()()){
                advancedNextIndex();
            }
        }

        @Override
        public boolean hasNext() {
            return nextIndex > -1 && nextIndex < elementData.length;
        }

        @Override
        public E next() {
            E nextElement = (E) elementData[nextIndex];
            advanceNextIndex();
            return nextElement;
        }

        private void advanceNextIndex(){
            nextIndex++;
//            while(nextIndex < elementData.length && (elementData[nextIndex] == null) || elementData[nextIndex]){
//                    nextIndex;
//            }
        }
    }
}

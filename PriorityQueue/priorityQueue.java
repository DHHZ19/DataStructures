package edu.ust.cisc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CiscPriorityQueue<E extends Comparable<E>> implements  CiscCollection<E> {

    private E[] elementData;

    private Comparator<E> comparator;

    private int size;

    public CiscPriorityQueue(){
        elementData = (E[]) new Comparable[10];
    }

    public CiscPriorityQueue(Comparator comparator){
        this();
        this.comparator = comparator;
    }
    public E peek(){
       if(size() == 0){
           throw new NoSuchElementException();
        }else{
           return elementData[0];
        }
    }
    public E remove(){
        if(size() == 0){
            throw new NoSuchElementException();
        }else{
            E removedEl = elementData[0];
            elementData[0] = null;




            return removedEl;
        }
    }
    public void add(E value){
        // resize if necessary
        if(size == elementData.length){
            elementData = Arrays.copyOf(elementData,
                    elementData.length * 2 + 1);
        }
        // place it at next open spot in underlying array of this heap which
        // is a tree
        elementData[size] = value;
        // bubble up with math to keep heap form
        int index = size;
        int parentIndex;
        boolean foundLocation = false;
        while(!foundLocation && hasParent(index)){
            parentIndex = parentIndex(index);
            if(compare(value, elementData[parentIndex]) > 0){
                swap(elementData, index, parentIndex);
                index = parentIndex;
            }else{
                foundLocation = true;
            }
        }
        size++;
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
    public boolean contains(Object o) {
        return false;
    }
    @Override
    public Iterator<E> iterator() {
        return new CiscPriorityQueueIterator();
    }

    @Override
    public Object[] toArray() {
     Object[] arr = new Object[size()];
     int j = 0;
     while(elementData[j] != null){
         arr[j] = elementData[j];
         j++;
     }
     return arr;
    }
    @Override
    public String toString(){
        String res = "";
        if(size() <= 0){
            return "[]";
        }else if(size() == 1){
            return res += "[" + elementData[0].toString() +  "]";
        }
        int arrSize = size();
        int j = 0;
        res += "[";
        while(arrSize != 0) {
            if(arrSize == 1) {
                res += elementData[j].toString();
            }else{
                res += elementData[j].toString() + ", ";
            }
            j++;
            arrSize--;
        }
        res += "]";
        return res;
    }

    public void clear() {
        int size = size();
        int j = 0;
        while(size != 0){
            elementData[j] = null;
            j++;
            size--;
        }
        this.size = 0;
    }
    private void swap(E[] arr, int index1, int index2){
        E temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
    private boolean hasParent(int index){
        return index > 0;
    }
    private int parentIndex(int index){
        return  (index - 1) / 2;
    }
    private int compare(E item1, E item2){
        if(comparator != null){
            return comparator.compare(item1, item2);
        }else{
            return item1.compareTo(item2);
        }
    }
    private class CiscPriorityQueueIterator implements Iterator<E> {
        private int nextIndex;

        public boolean hasNext(){
            return nextIndex < size;
        }

        public E next(){
            E element = elementData[nextIndex];
            nextIndex++;
            return element;
        }
    };
}

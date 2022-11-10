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
    /*
    The rightmost "leaf" in the max heap should become the new root then bubbled
    down to its final position. If a swap is needed and both children have the
    same value, your implementation should swap with the left
    child.
    */
    public E remove(){
        E result = peek();

        // move rightmost leaf to become new root
        elementData[0] = elementData[size-1];
        elementData[size-1] = null;
        size--;

        // bubble down
        int index = 0;
        boolean found = false;
        while(!found && hasLeftChild(index)) {
            int left = leftChild(index);
            int right = rightChild(index);
            int child = left;
            if (hasRightChild(index) && compare(elementData[right],
                    (elementData[left])) > 0) {
                child = right;
            }
            if (compare(elementData[index],elementData[child]) < 0){
                swap(elementData, index, child);
                index = child;
            }else{
                found = true; // found proper location; stop the loop
            }
        }
        return result;
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

    public boolean contains(Object value){
        return contains(value, 0);
    }

    private boolean contains(Object value, int index) {
        if(index >= size || compare(elementData[index], (E) value) < 0){
            return false;
        }else if(compare(elementData[index], (E) value) == 0){
            return true;
        }else{
            return contains(value, leftChild(index)) || contains(value,
                    rightChild(index));
        }
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
    private boolean hasLeftChild(int index){
        return elementData[index * 2 + 1] != null;
    }
    private boolean hasRightChild(int index){
        return elementData[index * 2 + 2] != null;
    }
    private int leftChild(int index){
        return index * 2 + 1;
    }
    private int rightChild(int index){
        return index * 2 + 2;
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

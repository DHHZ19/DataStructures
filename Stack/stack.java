package edu.ust.cisc;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Objects;

public class CiscStack<E> implements CiscCollection<E> {

    public static final int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int size;

    public CiscStack(){
        this(DEFAULT_CAPACITY);
    }
    public CiscStack(int initialCapacity){
        if(!(initialCapacity > 0)){
            throw new IllegalArgumentException();
        }
            elementData = (E[])new Object[initialCapacity];
            size = 0;
    }
    public E peek(){
        if(size() > 0)
            return elementData[size-1];
        throw new EmptyStackException();
    }

    public E pop(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        E item = elementData[size-1];
        elementData[size - 1] = null;
        size--;
      return item;
    }
    public void push(E item){
        ensureCapacity(size + 1);
        if(isEmpty() == true){
            elementData[0] = item;
            size += 1;
        }else{
            elementData[size] = item;
            size++;
        }

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        for(int i = 0; i < size; i++){
            if(Objects.equals(o, elementData[i])){
                return true;
            }
        }
        return false;
    }

    public Iterator<E> iterator() {
        return new StackIterator();
    }
    /*
    toArray() : Object[]
    Returns an array containing all the elements in the stack, beginning with the element at the top
    of the stack, ending with the element at the bottom of the stack, stored in consecutive indices
    of the returned array, starting with index 0. The length of the returned array is equal to size.
    */
    // [1,3,4,5]
    // [5,4,3,1]
    public Object[] toArray() {
        if(isEmpty()){
            return new Object[0];
        }
        Object[] arr = new Object[size];
        int j = size - 1;
        for(int i = 0; i < size; i++, j--){
            arr[i] = elementData[j];
        }
        return arr;
    }

    public void clear() {
       if(!isEmpty() == true){
           Arrays.fill(elementData, null);
           Arrays.copyOf(elementData, 0);
           size = 0;
       }
    }
    private void ensureCapacity(int minCapacity) {
        int currLength = elementData.length;
        int newCapacity = (currLength * 2) + 1;
        if (minCapacity > currLength) {
            if (newCapacity >= minCapacity) {
                minCapacity = newCapacity;
                elementData = (E[]) new Object[minCapacity];
            }
        }
    }
    private class StackIterator implements Iterator<E>{
        private int nextIndex;
        public StackIterator (){
            this.nextIndex = size - 1;
        }

        @Override
        public boolean hasNext() {
            if(isEmpty()){
                return false;
            }
            if(nextIndex >= 0){
                return true;
            }else{
                return false;
            }
        }
        @Override
        public E next(){
            E value = elementData[nextIndex];
            nextIndex--;
            return value;
        }
    }

    }

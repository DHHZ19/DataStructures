package edu.ust.cisc;

import java.util.Iterator;
import java.util.Arrays;
public class CiscArrayList<E> implements CiscList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size = 0;
    private E[] elementData;

    CiscArrayList() {
        this.elementData = (E[]) new Object[DEFAULT_CAPACITY];
    }

    CiscArrayList(int initialCapacity){
        if(initialCapacity <= 0){
            throw new IllegalArgumentException();
        }
        this.elementData = (E[]) new Object[initialCapacity];
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean contains(Object o) {
        for(int i = 0; i < size; i++){
            if(elementData[i] == o){
                return true;
            }
        }
        return false;
    }

    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        E[] array = (E[]) new Object[size];
        System.arraycopy(elementData, 0, array, 0, size);
        // Params:
        //src – the source array.
        // srcPos – starting position in the source array.
        // dest – the destination array.
        // destPos – starting position in the destination data.
        // length – the number of array elements to be copied.
        return array;
    }

    public boolean add(E e) {
        elementData[size++] = e;
        return true;
    }
    // [1,2,3,null,null]
    // 3 - 2 - 1
    // 0
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
            // Params:
            // src – the source array.
            // srcPos – starting position in the source array.
            // dest – the destination array.
            // destPos – starting position in the destination data.
            // length – the number of array elements to be copied.
            size--;
            elementData[size] = null;
            return true;
        }
        return false;
    }

    public void clear() {
        if(size > 0){
            Arrays.fill(elementData, 0, size, null);
            size = 0;
        }
    }

    public E get(int index) {
       for(int i = 0; i < size; i++){
           if(i == index){
               return elementData[index];
           }
       }
        throw new IndexOutOfBoundsException();
    }

    public E set(int index, E element) {
        E copy = (E) elementData[index];
        elementData[index] = element;
        return copy;
    }

    public void add(int index, E element) {
        for(int i = 0; i < size; i++){
            if(i == index){
                elementData[i] = element;
            }
        }
    }
    public E remove(int index) {
        if(index > elementData.length || index < 0){
            throw new IndexOutOfBoundsException();
        }
        E element = elementData[index];
        if (index != --size)
        System.arraycopy(elementData, index + 1, elementData, index, size - index);
        // Params:
        //src – the source array.
        // srcPos – starting position in the source array.
        // dest – the destination array.
        // destPos – starting position in the destination data.
        // length – the number of array elements to be copied.
        elementData[size] = null;
        return element;
        }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }
    public void ensureCapacity(int minimumCapacity){
        int dataLength = elementData.length;
        if(minimumCapacity > dataLength){
            E[] newData = (E[]) new Object[Math.max(dataLength * 2, minimumCapacity)];
            System.arraycopy(elementData, 0, newData, 0, size);
            elementData = newData;
        }
    }

}

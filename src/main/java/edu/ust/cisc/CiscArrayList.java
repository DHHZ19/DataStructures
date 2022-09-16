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
        return array;
    }

    public boolean add(E e) {
        elementData[size++] = e;
        return true;
    }
    public boolean remove(Object o) {
        for(int i = 0; i < size; i++){
            if(o == elementData[i]){
                for(int j = 0; j < size; j++){
                    elementData[j] = elementData[j + 1];
                }
                return true;
            }
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
        return null;
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

    }

}

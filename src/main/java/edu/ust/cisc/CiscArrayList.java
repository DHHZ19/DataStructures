package edu.ust.cisc;

import java.util.Iterator;

public class CiscArrayList<E> implements CiscList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size = 0;
    private final E[] elementData;

    CiscArrayList() {
        elementData = (E[]) new Object[DEFAULT_CAPACITY];
    }

    CiscArrayList(int initialCapacity){
        elementData = (E[]) new Object[initialCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<E> iterator() {
        return null;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public boolean add(E e) {
        return false;
    }
    public boolean remove(Object o) {
        return false;
    }

    public void clear() {

    }

    public E get(int index) {
        return null;
    }

    public E set(int index, E element) {
        return null;
    }

    public void add(int index, E element) {

    }

    public E remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == elementData[i]) {
                return i;
            }
        }
        return -1;
    }
    public void ensureCapacity(int minimumCapacity){

    }

}

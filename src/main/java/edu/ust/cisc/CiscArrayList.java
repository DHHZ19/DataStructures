package edu.ust.cisc;

public class CiscArrayList<E> implements CiscList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size = 0;
    private E[] arr;

    CiscArrayList() {
        arr = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == arr[i]) {
                return i;
            }
        }
        return -1;
    }
}

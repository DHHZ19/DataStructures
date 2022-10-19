package edu.ust.cisc;

import java.util.Iterator;
import java.util.Objects;

public class CiscQueue<E> implements CiscCollection<E> {
    public static final int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int frontIndex;
    private int rearIndex;

    public CiscQueue() {
        this(DEFAULT_CAPACITY);
    }

    public CiscQueue(int initalCapacity) {
        if (initalCapacity < 0) {
            throw new IllegalArgumentException();
        }
        elementData = (E[]) new Object[initalCapacity];
        frontIndex = -1;
        rearIndex = -1;
    }

    public boolean offer(E item) {
        ensureCapacity(size() + 1);
        elementData[(rearIndex + 1) % elementData.length] = item;
        rearIndex = (rearIndex + 1) % elementData.length;
        return true;
    }

    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E value = elementData[frontIndex];
        elementData[frontIndex] = null;
        if (frontIndex == rearIndex) {
            frontIndex = -1;
            rearIndex = -1;
        } else {
            frontIndex = (frontIndex + 1) % elementData.length;
        }
        return value;
    }

    public E peek() {
        return elementData[frontIndex];
    }

    @Override
    public int size() {
        if (rearIndex > frontIndex) {
            return (rearIndex - frontIndex) + 1;
        } else if (frontIndex > rearIndex) {
            return elementData.length - (frontIndex - rearIndex) + 1;
        } else if (frontIndex == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean isEmpty() {
        return frontIndex == -1;
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }
        int i = frontIndex;
        while (i != rearIndex) {
            if (Objects.equals(elementData[i], o)) {
                return true;
            }
            i = (i + 1) % elementData.length;
        }
        if (Objects.equals(elementData[i], o)) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscQueueIterator();
    }

    @Override
    public Object[] toArray() {
        if (isEmpty()) {
            return new Object[0];
        }
        Object[] arr = new Object[size()];
        int i = frontIndex;
        int j = 0;
        while (i != rearIndex) {
            arr[j] = elementData[i];
            i = (i + 1) % elementData.length;
            j++;
        }
        arr[j] = elementData[i];
        return arr;
    }

    @Override
    public void clear() {
        if (!isEmpty()) {
            int i = frontIndex;
            while (i != rearIndex) {
                elementData[i] = null;
                i = (i + 1) % elementData.length;
            }
            elementData[i] = null;
            frontIndex = -1;
            rearIndex = -1;
        }
    }
    
    private void ensureCapacity(int minCapacity) {
        if (frontIndex == -1) {
            frontIndex = 0;
            rearIndex = 0;
            Object[] newArr = (E[]) new Object[minCapacity];
            elementData = (E[]) newArr;
        }
        int currLength = elementData.length;
        int newCapacity = (currLength * 2) + 1;
        if (minCapacity > currLength) {
            if (newCapacity > currLength) {
                Object[] newArr = (E[]) new Object[newCapacity];
                int i = frontIndex;
                int j = 0;
                while (i != rearIndex) {
                    newArr[j] = elementData[i];
                    i = (i + 1) % elementData.length;
                    j++;
                }
                newArr[j] = elementData[i];
                frontIndex = 0;
                rearIndex = j;
                elementData = (E[]) newArr;
            }
        } else if (minCapacity == currLength) {
            Object[] newArr = (E[]) new Object[minCapacity];
            System.arraycopy(elementData, 0, newArr, 0, minCapacity);
            elementData = (E[]) newArr;
        }
    }

    private class CiscQueueIterator implements Iterator<E> {
        int numElementsReturned = 0;

        @Override
        public boolean hasNext() {
            return numElementsReturned < size();
        }

        @Override
        public E next() {
          int nextIndex = (frontIndex + numElementsReturned) % elementData.length;
          E value = elementData[nextIndex];
          numElementsReturned++;
          return value;
        }

    }
}

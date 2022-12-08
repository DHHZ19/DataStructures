import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.lang.model.element.Element;
import javax.swing.plaf.basic.BasicTreeUI;

// Implements a priority queue of comparable objects using a max-heap represented as an array.
public class CiscPriorityQueue<E extends Comparable<E>> implements CiscCollection<E> {
    private E[] elementData;
    private Comparator<E> comparator;
    private int size;

    public static void heapSort(Comparable[] unsortedArray, int size) {
        CiscPriorityQueue pq = buildInPlaceHeap(unsortedArray, size);
        sortInPlaceHeap(pq);
    }

    private static CiscPriorityQueue buildInPlaceHeap(Comparable[] unsortedArray, int size) {
        CiscPriorityQueue data = new CiscPriorityQueue();
        data.elementData = unsortedArray;

        while (data.size < size) {
            data.bubbleUp();
            data.size++;
        }
        return data;
    }

    private static void sortInPlaceHeap(CiscPriorityQueue pq) {
        while (!pq.isEmpty()) {
            pq.elementData[pq.size - 1] = pq.remove();
        }
    }

    public void bubbleUp() {
        int index = size;
        boolean found = false;
        while (!found && hasParent(index)) {
            int parent = parent(index);
            if (compare(elementData[index], elementData[parent]) > 0) {
                swap(elementData, index, parent(index));
                index = parent(index);
            } else {
                found = true;
            }
        }
    }

    // Constructs an empty queue.
    @SuppressWarnings("unchecked")
    public CiscPriorityQueue() {
        elementData = (E[]) new Comparable[10];
        size = 0;
    }

    public CiscPriorityQueue(Comparator<E> comparator) {
        this();
        this.comparator = comparator;
    }

    // Returns the maximum value in the queue without modifying the queue.
    // If the queue is empty, throws a NoSuchElementException.
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elementData[0];
    }

    // Removes and returns the maximum value in the queue.
    // If the queue is empty, throws a NoSuchElementException.
    public E remove() {
        E result = peek();

        // move rightmost leaf to become new root
        elementData[0] = elementData[size - 1];
        elementData[size - 1] = null;
        size--;

        // "bubble down" root as necessary to fix ordering
        bubbleDown(0);

        return result;
    }

    private int compare(E item1, E item2) {
        if (comparator == null) {
            return item1.compareTo(item2);
        } else {
            return comparator.compare(item1, item2);
        }
    }

    // Swaps the element at the given index as necessary to fix ordering.
    private void bubbleDown(int index) {
        // while hasLeftChild
        while (hasLeftChild(index)) {
            int left = leftChild(index);
            int right = rightChild(index);
            int child = left;
            // if hasRightChild and right is bigger, then swap with it
            if (hasRightChild(index) && compare(elementData[right], (elementData[left])) > 0) {
                child = right;
            }
            // swap parent with child
            if (compare(elementData[index], elementData[child]) < 0) {
                swap(elementData, index, child);
                index = child;
            } else {
                break; // found proper location; stop the loop
            }
        }
    }

    // helpers for navigating index up/down the tree
    private int parent(int index) {
        return (index - 1) / 2;
    }

    // returns index of left child of given index
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    // returns index of right child of given index
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    // returns true if the node at the given index has a parent (is not the root)
    private boolean hasParent(int index) {
        return index > 0;
    }

    // returns true if the node at the given index has a non-empty left child
    private boolean hasLeftChild(int index) {
        return leftChild(index) < size;
    }

    // returns true if the node at the given index has a non-empty right child
    private boolean hasRightChild(int index) {
        return rightChild(index) < size;
    }

    // switches the values at the two given indexes of the given array
    private void swap(E[] a, int index1, int index2) {
        E temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
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
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public void clear() {

    }
}

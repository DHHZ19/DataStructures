package edu.ust.cisc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class CiscSortedLinkedList <E extends Comparable<E>> implements CiscList<E>{
    private Node<E> head;

    private int size;

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
        if(size == 0){
            return false;
        }else if(Objects.equals(head.data, o)){
            return true;
        }else{
            return contains(o, head);
        }
    }
    private boolean contains(Object o, Node<E> node){
        if(node.next == null){
            return false;
        }else if(node.next.data.compareTo((E) o) > 0){
            return false;
        }else if(node.next.data.compareTo((E) o) == 0){
            return true;
        }else{
            return contains(o, node.next);
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CiscLinkedListIterator();
    }
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        toArray(array, 0, head);
        return array;
    }
    private void toArray(Object[] array, int idx, Node<E> node){
        if(node == null){
            return;
        }else{
            array[idx] = node.data;
            toArray(array,  idx + 1,node.next);
        }
    }
    @Override
    public boolean add(E e) {
        if(head == null || e.compareTo(head.data) < 0){
            head = new Node(e, head);
        }else {
            add(e, head);
        }
        size++;
        return true;
    }
    private void add(E e, Node<E> node){
        if(node.next == null){
            node.next = new Node(e, null);
        } else if(e.compareTo(node.next.data) <  0){
            node.next = new Node(e, node.next);
        }else{
          add(e, node.next);
        }
    }

    @Override
    public boolean remove(Object o) {
       if(size == 0){
           return false;
       }else if(Objects.equals(head.data, o)){
           head = head.next;
           size--;
           return true;
       }else{
           return remove(o, head);
       }
    }
    private boolean remove(Object o, Node<E> node){
        if(node.next == null){
            return false;
        }else if(node.next.data.compareTo((E) o) > 0){
            return false;
        }else if(node.next.data.compareTo((E) o) == 0){
             node.next = node.next.next;
             size--;
             return true;
        }else{
            return remove(o, node.next);
        }

    }

    @Override
    public void clear() {
        if(head == null){
            return;
        }
        head = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        return get(index, 0, head);
    }

    private E get(int idx, int currIdx, Node<E> node){
        if(currIdx == idx){
            return node.data;
        }
        return get(idx, currIdx + 1, node.next);
    }


    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
       throw new UnsupportedOperationException();
    }
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            head = head.next;
            size--;
            return null;
        } else {
            return remove(index, 0, head);
        }
    }
//  remove(index : int, currentIndex : int, n : Node<E>) : E,
    private E remove(int idx, int currIdx, Node<E> node){
        if(currIdx + 1 == idx){
            node.next = node.next.next;
            size--;
            return node.data;
        }
        return  remove(idx, currIdx + 1, node.next);
    }



    @Override
    public int indexOf(Object o) {
        return indexOf(o, 0, head);
    }
    private int indexOf(Object o, int currIdx, Node<E> node){
        if(node == null){
            return -1;
        }else if(Objects.equals(o, node.data)){
            return currIdx;
        }
        return indexOf(o, currIdx + 1, node.next);
    }
    public String toString(){
        String result;
        if(head == null){
            result = "[]";
        }else{
            result = "[";
            result += toString(head);
            result += "]";
        }
        return result;
    }
    private String toString(Node<E> node){
        if (node.next == null) {
            return node.data.toString();
        }else {
            return node.data.toString() + ", " + toString(node.next);
        }
    }
    // static because it doesn't have to worry about containing class
    private static class Node<E> {
        private E data;
        private Node<E> next;

        private Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
    private class CiscLinkedListIterator implements Iterator<E>{
        private Node<E> nextNode;
        private int nextNodeIndex;

        public CiscLinkedListIterator (){
            this.nextNode = head;
            this.nextNodeIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return nextNodeIndex < size;
        }
        @Override
        public E next(){
            E value = nextNode.data;
            nextNode = nextNode.next;
            nextNodeIndex++;
            return value;
        }
    }

}
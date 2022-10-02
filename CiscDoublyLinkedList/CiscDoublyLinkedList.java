package edu.ust.cisc;

import java.util.Iterator;
import java.util.Objects;

public class CiscDoublyLinkedList<E> implements CiscList<E>  {
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
        if(size <= 0){
           return false;
        }
        if(indexOf(o) <= size && indexOf(o) >= 0){
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscDoublyLinkedListIterator();
    }

    @Override
    public Object[] toArray() {
        if(size <= 0){
            return new Object[0];
        }
        Object[] array = new Object[size];
        Node node = new Node(head.data, head.next, head.prev);
        for(int i = 0; i < size; i++){
            array[i] = node.data;
            node = node.next;
        }
        return array;
    }

    @Override
    public boolean add(E e) {
        if(size == 0){
            Node node = new Node(e, null, null);
            node.prev = node;
            node.next = node;
            head = node;
        }else{
            Node node = new Node(e, head, head.prev);
            head.prev.next = node;
            head.prev = node;
        }
        size++;

        return false;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> node = head;
        for(int i = 0; i < size; i++){
            if(Objects.equals(node.data, o)){
                Node<E> old = node;
                node.prev.next = node.next;
                old.next.prev = node.prev;
                if(node == head) {
                   if(size == 1){
                       head = null;
                   }else{
                       head = head.next;
                   }
                }
                size--;
                return true;
            }else{
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        Node temp = new Node(null, null, null);
        if(this.head != null) {
            temp = this.head;
            this.head = this.head.next;
            temp = null;
            this.head = null;
            size = 0;
        }
    }

    @Override
    public E get(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        CiscDoublyLinkedListIterator iter = new CiscDoublyLinkedListIterator();
        while(iter.hasNext()){
            if(iter.nextNodeIndex != index){
                iter.next();
            }else if(iter.nextNodeIndex == index){
                return iter.next();
            }
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = new Node(head.data, head.next, head.prev);
        if(0 == index){
            Node<E> old = new Node(node.data, node.next, node.prev);
            head.data = element;
            return old.data;
        }
        for(int i = 0; i < size; i++){
          if(i == index){
              Node<E> old = new Node(node.data, node.next, node.prev);
              node.data = element;
              return old.data;
          }else{
             node = node.next;
          }
        }
        return element;
    }

    @Override
    public void add(int index, E element) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        if(head == null){
            Node node = new Node(element, null, null);
            node.prev = node;
            node.next = node;
            head = node;
        }else {
            Node node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            Node newNode = new Node(element, node, node.prev);
            node.prev.next = newNode;
            node.prev = newNode;
            // upades if necessary
            if(index == 0){
                head = newNode;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        Node<E> node =  head;
        for(int i = 0; i < size; i++){
            if(i == index){
                Node<E> old = node;
                old.data = node.data;
                node.prev.next = node.next;
                node.next.prev = node.prev;
                if(node == head){
                    if(size == 1){
                        head = null;
                    }else{
                        head = head.next;
                    }
                }
                size--;
                return old.data;
            }else{
                node = node.next;
            }
        }
        return node.data;
    }

    @Override
    public int indexOf(Object o) {
        Node node = new Node(head.data, head.next, head.prev);
        for(int i = 0; i < size; i++){
          if(node.data.equals(o)){
              return i;
          }else{
              node = node.next;
          }

        }
        return -1;
    }
    // static because it doesn't have to worry about containing class
    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        private Node(E data, Node<E> next, Node<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
    private class CiscDoublyLinkedListIterator implements Iterator<E>{
        private Node<E> nextNode;
        private int nextNodeIndex;

        public CiscDoublyLinkedListIterator (){
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

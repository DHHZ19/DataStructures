package edu.ust.cisc;

public class CiscArrayList<E> implements CiscList<E> {
    private int DEFAULT_CAPACITY = 10;
    private int size = 0;
    private Object E[];

   public CiscArrayList(){
    elements = new Object [DEFAULT_CAPACITY];
    }
    public CiscArrayList(){
        elements = new Object [DEFAULT_CAPACITY];
    }

}

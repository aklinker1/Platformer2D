package com.klinker.engine2d.utils;

import java.util.LinkedList;

public class Queue<T> {

    private LinkedList<T> list;
    private boolean loop;

    public Queue() {
        this(false);
    }

    public Queue(boolean loop) {
        this.list = new LinkedList<>();
        this.loop = loop;
    }

    public void enqueue(T t) {
        list.addLast(t);
    }

    public T dequeue() {
        T result = list.removeFirst();
        if (loop) enqueue(result);
        return result;
    }

    public void setIsLooping(boolean isLooping) {
        this.loop = isLooping;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T peek() {
        return list.getFirst();
    }

}

package com.dabinsystems.pact_app.Util;

import java.util.ArrayDeque;

public class DequeBasedSynchronizedStack <E> {

    // Internal Deque which gets decorated for synchronization.
    private ArrayDeque<E> dequeStore;

    public DequeBasedSynchronizedStack(int initialCapacity) {
        this.dequeStore = new ArrayDeque<>(initialCapacity);
    }

    public DequeBasedSynchronizedStack() {
        dequeStore = new ArrayDeque<>();
    }

    public synchronized E pop() {
        return this.dequeStore.pop();
    }

    public synchronized void push(E element) {
        this.dequeStore.push(element);
    }

    public synchronized E peek() {
        return this.dequeStore.peek();
    }

    public synchronized int size() {
        return this.dequeStore.size();
    }
}

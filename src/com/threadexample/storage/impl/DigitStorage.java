package com.threadexample.storage.impl;

import com.threadexample.storage.Storage;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by araksgyulumyan
 * Date - 1/9/18
 * Time - 5:13 PM
 */
public class DigitStorage implements Storage {

    // Constants
    private static final int STORAGE_SIZE = 100;

    private static final int SIZE_THRESHOLD = 80;

    private static Boolean wasFull = Boolean.FALSE;

    private Boolean isPutActionStopped = Boolean.FALSE;

    private final ReentrantLock lock = new ReentrantLock();

    // Properties
    private final Queue<Integer> queue;

    public DigitStorage() {
        this.queue = new ArrayBlockingQueue<>(STORAGE_SIZE);
    }

    @Override
    public Integer get() {
        System.out.println("Size - " + queue.size());
        return queue.poll();
    }

    @Override
    public void put(int digit) {
        if (isFull()) {
            wasFull = Boolean.TRUE;
            System.out.println("Queue is full, preventing producers to act till the size will be less than - " + SIZE_THRESHOLD);
            return;
        }
        if (wasFull) {
            if (getSize() >= SIZE_THRESHOLD) {
                return;
            }
            wasFull = Boolean.FALSE;
        }
        queue.add(digit);
        System.out.println("Size - " + queue.size());
    }

    public Boolean getPropertyIsPutActionStopped() {
        lock.lock();
        try {
            return isPutActionStopped;
        } finally {
            lock.unlock();
        }
    }

    public void setPropertyIsPutActionStopped(Boolean putActionStopped) {
        lock.lock();
        try {
            isPutActionStopped = putActionStopped;
        } finally {
            lock.unlock();
        }
    }

    // Utility methods
    private boolean isFull() {
        return queue.size() == STORAGE_SIZE;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    private int getSize() {
        return queue.size();
    }

}

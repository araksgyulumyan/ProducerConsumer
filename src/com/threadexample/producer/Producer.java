package com.threadexample.producer;


import com.threadexample.storage.impl.DigitStorage;

import java.util.Random;

/**
 * Created by araksgyulumyan
 * Date - 1/9/18
 * Time - 3:49 PM
 */
public class Producer implements Runnable {

    private final DigitStorage storage;

    public Producer(final DigitStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                storage.put(random.nextInt());
                Thread.sleep(1000);
                System.out.println("Added number");
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted");
            }
        }
    }
}

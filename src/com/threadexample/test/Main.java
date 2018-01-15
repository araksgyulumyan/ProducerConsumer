package com.threadexample.test;

import com.threadexample.consumer.Consumer;
import com.threadexample.producer.Producer;
import com.threadexample.storage.impl.DigitStorage;

import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by araksgyulumyan
 * Date - 1/9/18
 * Time - 3:48 PM
 */
public class Main {
    private static final int PRODUCERS_QUANTITY = 4;
    private static final int CONSUMERS_QUANTITY = 1;
    private static final String FILE_PATH_TO_WRITE = "/Users/araksgyulumyan/Desktop/testfile.txt";

    public static void main(String[] args) {

        DigitStorage storage = new DigitStorage();
        final Date startTime = new Date();

        final Set<Thread> producerThreads = new HashSet<>(PRODUCERS_QUANTITY);
        for (int i = 0; i < PRODUCERS_QUANTITY; i++) {
            Thread producer = new Thread(new Producer(storage));
            producerThreads.add(producer);
            producer.start();
        }

        final Set<Thread> consumerThreads = new HashSet<>(CONSUMERS_QUANTITY);
        for (int i = 0; i < CONSUMERS_QUANTITY; i++) {
            Thread consumer = new Thread(new Consumer(storage));
            consumerThreads.add(consumer);
            consumer.start();
        }

        boolean timePassed = Boolean.FALSE;
        while (true) {
            if (new Date().getTime() - startTime.getTime() >= 60000) {
                timePassed = Boolean.TRUE;
            }
            if (timePassed) {
                break;
            }
        }

        for (final Thread thread : producerThreads) {
            thread.stop();
        }

        storage.setPropertyIsPutActionStopped(timePassed);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (storage.isEmpty()) {
                for (final Thread thread : consumerThreads) {
                    thread.stop();
                }
                break;
            }
        }

        FileReader fileReader;
        try {
            String fileContent;
            fileReader = new FileReader(FILE_PATH_TO_WRITE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            fileContent = bufferedReader.readLine();

            if (fileContent != null && fileContent.length() > 0 && fileContent.charAt(fileContent.length() - 1) == ',') {
                String resultString = fileContent.substring(0, fileContent.length() - 1);
                FileWriter fileWriter = new FileWriter(FILE_PATH_TO_WRITE);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                try {
                    bufferedWriter.write(resultString);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    bufferedWriter.close();
                    fileWriter.close();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("Finished");
    }
}

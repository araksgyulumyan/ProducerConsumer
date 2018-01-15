package com.threadexample.consumer;

import com.threadexample.storage.impl.DigitStorage;

import java.io.*;

/**
 * Created by araksgyulumyan
 * Date - 1/9/18
 * Time - 3:49 PM
 */
public class Consumer implements Runnable {
    private final DigitStorage storage;
    private static final String FILE_PATH_TO_WRITE = "/Users/araksgyulumyan/Desktop/testfile.txt";

    public Consumer(final DigitStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (storage.getPropertyIsPutActionStopped()) {
                    try {
                        Integer queueElement = storage.get();
                        if (queueElement != null) {
                            String fileContent;
                            FileReader fileReader = new FileReader(FILE_PATH_TO_WRITE);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            fileContent = bufferedReader.readLine();
                            String stringFormatting = queueElement + ",";

                            String resultString = (fileContent == null) ? "" + stringFormatting : fileContent + stringFormatting;

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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    storage.get();
                    Thread.sleep(1000);
                    System.out.println("Consumed number");
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
            }
        }
    }
}

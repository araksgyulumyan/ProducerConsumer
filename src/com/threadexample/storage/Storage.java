package com.threadexample.storage;


/**
 * Created by araksgyulumyan
 * Date - 1/9/18
 * Time - 4:05 PM
 */
public interface Storage {

    /**
     * Gets item from storage
     */
    Integer get();

    /**
     * Puts item into storage
     *
     * @param digit
     */
    void put(final int digit);
}

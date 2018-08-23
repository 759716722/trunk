package com.jwei.utils;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wyb on 2018/7/31.
 */
public class MemoryTest {

    static Collection objects = new ArrayList();
    static long lastMaxMemory = 0;

    static void printMaxMemory() {
        long currentMaxMemory = Runtime.getRuntime().maxMemory();
        if (currentMaxMemory != lastMaxMemory) {
            lastMaxMemory = currentMaxMemory;
            System.out.format("Runtime.getRuntime().maxMemory(): %,dK.%n", currentMaxMemory / 1024);
        }
    }

    static void consumeSpace() {
        objects.add(new int[1_000_000]);
    }

    static void freeSpace() {
        objects.clear();
    }

    public static void main(String[] args) {
        try {
            List inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            System.out.println("Running with: " + inputArguments);
            while (true) {
                printMaxMemory();
                consumeSpace();
            }
        } catch (OutOfMemoryError e) {
            freeSpace();
            printMaxMemory();
        }
    }

}

package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        Set<String> files = new HashSet<>(Arrays.asList(args));
        ArrayList<Thread> threads = new ArrayList<>();

        for (String filename : files) {
            Thread th = new Thread(new DocumentThread(filename));
            threads.add(th);
            System.out.println("Starting " + th.getName());
            th.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DocumentParser.totalNumberOfWords();
    }
}

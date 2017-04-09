package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Set<String> files = new HashSet<>(Arrays.asList(args));

        for (String filename : files) {
            Thread th = new Thread(new DocumentThread(filename));
            System.out.println("Starting " + th.getName());
            th.start();
        }
    }
}

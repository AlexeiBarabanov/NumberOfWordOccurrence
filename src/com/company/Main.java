package com.company;

public class Main {

    public static void main(String[] args) {

        DocumentReader[] documents = new DocumentReader[args.length];
        for (int i = 0; i < args.length; i++) {
            documents[i] = new DocumentReader();
            System.out.println(documents[i].read(args[i]));
        }
    }
}

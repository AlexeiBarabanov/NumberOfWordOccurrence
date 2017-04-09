package com.company;

public class Main {

    public static void main(String[] args) {

        DocumentReader[] documents = new DocumentReader[args.length];
        for (int i = 0; i < args.length; i++) {
            documents[i] = new DocumentReader();
            WordProcessor wp = new WordProcessor(documents[i].read(args[i]));
            wp.process();
            wp.printResult();
        }


//
//        Thread t1 = new Thread(new Test());
//        Thread t2 = new Thread(new Test());
//        Thread t3 = new Thread(new Test());
//        t1.start();
//        t2.start();
//        t3.start();

    }
}

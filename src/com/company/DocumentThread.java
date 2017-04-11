package com.company;

import java.io.FileNotFoundException;

public class DocumentThread implements Runnable {

    final private String filename;

    public DocumentThread(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        String text = "";
        try {
            text = new DocumentReader().read(this.filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DocumentParser parser = new DocumentParser(text, filename);
        try {
            parser.start();
            System.out.println(parser + System.lineSeparator());
        } catch (DocumentParser.UnacceptableSymbolFound e) {
            e.printStackTrace();
        }
    }
}

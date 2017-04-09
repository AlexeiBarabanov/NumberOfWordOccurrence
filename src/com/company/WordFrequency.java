package com.company;

public class WordFrequency {

    final private String word;
    private int frequency;
//    private Set<String> documentIDs;

    public WordFrequency(String word) {
        this.word = word;
        this.frequency = 1;
//        this.documentIDs = new HashSet<>();
    }

    public synchronized void increaseFrequency() {
        this.frequency++;
    }

    @Override
    public String toString() {
        return " [" + frequency + ":" + word + "] ";
    }
}

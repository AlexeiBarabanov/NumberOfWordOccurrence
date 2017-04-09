package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordProcessor {

    private final String text;
    static final private Map<String, WordFrequency> wordFrequencies;

    static {
        wordFrequencies = new ConcurrentHashMap<>();
    }

    public WordProcessor(String text) {
        this.text = text;
    }

    static public void printResult() {
        wordFrequencies.forEach( (k, v) -> System.out.println("    " + v + " = " + k));
        System.out.println("Total distinct words: " + wordFrequencies.size());
    }

    public void process() {

        String words = this.text.replaceAll("-", " ") // в некоторых текстах не правильно используют тире
                .replaceAll("[^A-Za-z0-9А-Яа-я ]", "");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(words.split("\\s+")));

        for (String word : list) {
            if(word != null) {
                WordFrequency frequency = wordFrequencies.get(word);
                if(frequency == null) {
                    frequency = new WordFrequency(word);
                    mapWord(word, frequency);
                } else {
                    frequency.increaseFrequency();
                }
            }
        }
    }

    private void mapWord(String word, WordFrequency frequency) {
        wordFrequencies.put(word, frequency);
    }
}

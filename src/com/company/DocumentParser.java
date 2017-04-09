package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DocumentParser {

    private final String text;
    static final private Map<String, WordFrequency> wordFrequencies;

    static {
        wordFrequencies = new ConcurrentHashMap<>();
    }

    public DocumentParser(String text) {
        this.text = text;
    }

    static public void printWordOccurences() {
        wordFrequencies.forEach((k, v) -> System.out.println(v + ":" + k));
    }

    @Override
    public String toString() {
        return "Total distinct words: " + wordFrequencies.size();
    }

    public void start() {
        int newWordsCount = 0;
        int distinctWordsCount = 0;

        String words = this.text.replaceAll("[^А-Яа-я ]", " ");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(words.split("\\s+")));

        for (String word : list) {
            if (word != null && !word.isEmpty()) {
                WordFrequency frequency = wordFrequencies.get(word);
                if (frequency == null) {
                    distinctWordsCount++;
                    frequency = new WordFrequency(word);
                    putWord(word, frequency);
                    newWordFound(frequency);
                } else {
                    newWordsCount++;
                    frequency.increaseFrequency();
                    wordOccurrenceFound(frequency);
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + ":done, new distinct words found " + distinctWordsCount
                + " out of a total of " + (newWordsCount + distinctWordsCount));
    }

    private void putWord(String word, WordFrequency frequency) {
        wordFrequencies.put(word, frequency);
    }

    public void wordOccurrenceFound(WordFrequency frequency) {
        System.out.println(Thread.currentThread().getName() + "  found occurrence of" + frequency);
    }

    public void newWordFound(WordFrequency frequency) {
        System.out.println(Thread.currentThread().getName() + "       new word found" + frequency);
    }
}

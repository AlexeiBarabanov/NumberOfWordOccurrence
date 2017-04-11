package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DocumentParser {

    private final String filename;
    private final String text;
    static final private Map<String, WordFrequency> wordFrequencies;
    static volatile boolean unacceptableSymbolFound;

    static {
        wordFrequencies = new HashMap<>();
        unacceptableSymbolFound = false;
    }

    public DocumentParser(String text, String filename) {
        this.text = text;
        this.filename = filename;
    }

    static public void printWordOccurences() {
        wordFrequencies.forEach((k, v) -> System.out.println(v + ":" + k));
    }

    @Override
    public String toString() {
        return "Total distinct words: " + wordFrequencies.size();
    }

    public void start() throws UnacceptableSymbolFound {
        if (unacceptableSymbolFound)
            throw new UnacceptableSymbolFound();

        int newWordsCount = 0;
        int distinctWordsCount = 0;

        String check = this.text.replaceAll("[A-Za-z]", " ");
//        if (!Pattern.matches("[0-9а-яА-ЯёЁ :;!?.,\"'()//\\r\\n]+", text)) {
        if(check.length() != this.text.length()) {
            this.unacceptableSymbolFound = true;
            System.out.println(Thread.currentThread().getName() + ": latin symbol occurred");
            throw new UnacceptableSymbolFound(filename);
        }

        String words = this.text.replaceAll("[^А-Яа-я ]", " ");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(words.split("\\s+")));

        for (String word : list) {
            if (unacceptableSymbolFound)
                throw new UnacceptableSymbolFound();

            if (word != null && !word.isEmpty()) {
                synchronized (wordFrequencies) {
                    WordFrequency frequency = getWord(word);
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
        }

        System.out.println(Thread.currentThread().getName() + ":done, new distinct words found " + distinctWordsCount
                + " out of a total of " + (newWordsCount + distinctWordsCount));
    }

    public WordFrequency getWord(String word) {
        WordFrequency frequency = wordFrequencies.get(word);
        return frequency;
    }

    public void putWord(String word, WordFrequency frequency) {
        wordFrequencies.put(word, frequency);
    }

    public void wordOccurrenceFound(WordFrequency frequency) {
        System.out.println(Thread.currentThread().getName() + "  found occurrence of" + frequency);
    }

    public void newWordFound(WordFrequency frequency) {
        System.out.println(Thread.currentThread().getName() + "       new word found" + frequency);
    }

    public class UnacceptableSymbolFound extends Throwable {
        public UnacceptableSymbolFound() {
            super(Thread.currentThread().getName() + " stopped it's work, due to the occurrence of a latin symbol in one of the threads ");
        }

        public UnacceptableSymbolFound(String filename) {
            super(Thread.currentThread().getName() + " stopped it's work, due to the occurrence of a latin symbol: " + filename);
        }
    }

    static public void printTotalWords() {
        int counter = 0;
        for (Map.Entry<String, WordFrequency> entry : wordFrequencies.entrySet()) {
            counter += entry.getValue().getFrequency();
        }
        System.out.println("Total words processed " + counter);
    }
}

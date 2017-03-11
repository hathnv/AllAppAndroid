package com.hust.thanglv.nlpkimdung.model;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/11/17.
 */

public class SplitText {
    private String str;

    public SplitText() {
    }

    public SplitText(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    /**
     * Tách các câu trong văn bản
     * @return danh sách các câu đã được tách khỏi văn bản
     */
    public List<String> splitSentences() {
        List<String> lsWords = new ArrayList<>();
        str = str.replaceAll("\\-", ' ' + "");
        str = str.replaceAll("\\?", ' ' + "");
        str = str.replaceAll("\\,", ' ' + "");
        str = str.replaceAll("\\.", ' ' + "");
        str = str.replaceAll("\\;", ' ' + "");
        str = str.replaceAll("\\!", ' ' + "");
        str = str.replaceAll("\"", ' ' + "");
        str = str.replaceAll(" \"", ' ' + "");
        str = str.replaceAll("\\/", ' ' + "");
        str = str.replaceAll("\\.\\.\\.", ' ' + "");
        str = str.replaceAll("\\:", ' ' + "");
        str = str.replaceAll("\\)", ' ' + "");
        str = str.replaceAll("\\(", ' ' + "");
        str = str.replaceAll("\\]", ' ' + "");
        str = str.replaceAll("\\[", ' ' + "");
        str = str.replaceAll("\\}", ' ' + "");
        str = str.replaceAll("\\{", ' ' + "");
        str = str.replaceAll("\\^", ' ' + "");
        str = str.replaceAll("\\'", ' ' + "");
        str = str.replaceAll("\\...", ' ' + "");
        str = str.replaceAll("\\…", ' ' + "");
        str = str.replaceAll("\\. . . .", ' ' + "");
        str = str.replaceAll("\\. . .", ' ' + "");
        str = str.replaceAll("\\. . . . . .", ' ' + "");

        String[] words = str.trim().replaceAll("\\s+", " ").trim().split(" ");
        for (String word : words) {
            word = word.trim();
            lsWords.add(word);
        }
        return lsWords;
    }

    /**
     * Tách các từ riêng biệt
     * @return danh sách các từ đã được tách
     */
    public List<String> splitText() {
        List<String> words = new ArrayList<>();
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(str);
        int start = boundary.first();
        for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
            if (!Character.isLetter(str.charAt(start))) {
                continue;
            }
            words.add(str.substring(start, end));
        }

        return words;
    }

}

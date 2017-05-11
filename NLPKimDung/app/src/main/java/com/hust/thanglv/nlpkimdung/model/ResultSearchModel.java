package com.hust.thanglv.nlpkimdung.model;

/**
 * Created by user on 3/14/17.
 */

public class ResultSearchModel {
    private Chapter chapter;
    private String content;
    private String numChapter, textSearch;
    int posSearchText;

    public ResultSearchModel() {
    }

    public ResultSearchModel(Chapter chapter, String content, int searchText, String numChapter, String textSearch) {
        this.chapter = chapter;
        this.content = content;
        this.posSearchText = searchText;
        this.numChapter = numChapter;
        this.textSearch = textSearch;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosSearchText() {
        return posSearchText;
    }

    public void setPosSearchText(int posSearchText) {
        this.posSearchText = posSearchText;
    }

    public String getNumChapter() {
        return numChapter;
    }

    public void setNumChapter(String numChapter) {
        this.numChapter = numChapter;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }
}

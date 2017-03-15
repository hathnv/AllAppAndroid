package com.hust.thanglv.nlpkimdung.model;

/**
 * Created by user on 3/14/17.
 */

public class ResultSearchModel {
    private String chapter;
    private String content;

    public ResultSearchModel() {
    }

    public ResultSearchModel(String chapter, String content) {
        this.chapter = chapter;
        this.content = content;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

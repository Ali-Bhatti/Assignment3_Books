package edu.pk.pucit.booksdata;

import java.util.ArrayList;

public class Book {
    private final String bkTitle;
    private final String bkLevel;
    private final String bkInfo;
    private final String bkUrl;
    private final String bkImgPaths;

    public Book(String bookTitle, String bookLevel, String bookInfo, String bookUrl, String bookImgPaths){
        this.bkTitle = bookTitle;
        this.bkLevel = bookLevel;
        this.bkInfo = bookInfo;
        this.bkImgPaths = bookImgPaths;
        this.bkUrl = bookUrl;
    }

    public String getBookTitle() {
        return bkTitle;
    }

    public String getBookLevel() {
        return bkLevel;
    }

    public String getBookInfo() {
        return bkInfo;
    }

    public String getBookUrl() {
        return bkUrl;
    }

    public String getBookImgPaths() {
        return bkImgPaths;
    }
}

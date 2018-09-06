package com.lly.test.export.excel.poi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private int bookId;
    private String name;
    private String author;
    private float price;
    private String isbn;
    private String pubName;
    private byte[] preface;

    public Book() {
    }

    public Book(int bookId, String name, String author, float price, String isbn, String pubName, byte[] preface) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
        this.pubName = pubName;
        this.preface = preface;
    }
}

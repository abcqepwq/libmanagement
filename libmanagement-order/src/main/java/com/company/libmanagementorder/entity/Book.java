package com.company.libmanagementorder.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Book {
    private long bookId;
    private String bookName;
    private String author;
    private boolean inactive;
    private int inventory;
    private int totalInventory;

    public Book(String bookName, String author){
        this.bookName = bookName;
        this.author = author;
    }
}

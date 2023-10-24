package com.company.libmanagementmain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Book {
    private Long bookId;
    private String bookName;
    private String author;
    private Boolean inactive;
    private Integer inventory;
    private Integer totalInventory;

    public Book(String bookName, String author){
        this.bookName = bookName;
        this.author = author;
    }
}

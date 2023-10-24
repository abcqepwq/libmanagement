package com.company.libmanagementbook.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BookWithOrder extends Book{
    private List<Order> orderList;

    public BookWithOrder(String bookName, String author){
        super(bookName, author);
    }
}
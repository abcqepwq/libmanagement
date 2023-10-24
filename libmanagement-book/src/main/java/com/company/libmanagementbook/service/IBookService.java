package com.company.libmanagementbook.service;

import com.company.libmanagementbook.entity.Book;

import java.util.List;

public interface IBookService {
    Book addBook(Book book, int number);

    boolean addBookInventory(Book book, int number);

    List<Book> getBookList(int offset, int size);

    Book getBookByNameAndAuthor(String bookName, String author);

    Book getBook(long bookId);

    Book updateBook(Book book, int number);

    int deleteBook(long bookId);
}

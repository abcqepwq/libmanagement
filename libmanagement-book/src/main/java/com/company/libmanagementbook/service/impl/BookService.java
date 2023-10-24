package com.company.libmanagementbook.service.impl;

import com.company.libmanagementbook.entity.Book;
import com.company.libmanagementbook.mapper.BookMapper;
import com.company.libmanagementbook.mapper.OrderMapper;
import com.company.libmanagementbook.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService implements IBookService {
    @Autowired
    BookMapper bookMapper;

    @Autowired
    OrderMapper orderMapper;

    @Transactional
    @Override
    public Book addBook(Book book, int number) {
        if(!book.isInactive()){
            bookMapper.addBook(book);
        }else{
            bookMapper.inactiveBook(false, book.getBookId());
        }
        bookMapper.addBookInventory(book.getBookId(), number);
        return bookMapper.getBook(book.getBookId());
    }

    public Book getBook(long bookId){
        return bookMapper.getBook(bookId);
    }

    @Override
    public boolean addBookInventory(Book book, int number) {
        return false;
    }

    @Override
    public List<Book> getBookList(int offset, int size) {
        int total = bookMapper.getTotalCount(size);
        if(offset >= total){
            offset = total - size >=0 ? total - size : 0;
            return bookMapper.getBookList(offset, size);
        }if(offset < 0){
            return bookMapper.getBookList(0, size);
        }
        else{
            return bookMapper.getBookList(offset, size);
        }

    }

    @Override
    public Book getBookByNameAndAuthor(String bookName, String author) {
        return bookMapper.getBookByNameAndAuthor(bookName, author);
    }

    public Book getInactiveBookByNameAndAuthor(String bookName, String author) {
        return bookMapper.getInactiveBookByNameAndAuthor(bookName, author);
    }

    @Override
    public Book updateBook(Book book, int number){
        bookMapper.udpateBookInventory(book.getBookId(), number);
        return bookMapper.getBook(book.getBookId());
    }

    @Transactional
    @Override
    public int deleteBook(long bookId){
        if (orderMapper.getOrderListbyBookId(bookId).size() > 0){
            return -1;
        }
        bookMapper.deleteBookInventory(bookId);
        return bookMapper.inactiveBook(true,bookId);
    }
}


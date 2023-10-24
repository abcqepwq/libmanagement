package com.company.libmanagementorder.mapper;

import com.company.libmanagementorder.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    boolean addBook(Book book);

    boolean addBookInventory(@Param("bookId")long bookId, @Param("inventory")int number);

    Book getBook(@Param("bookId")long bookId);

    Book udpateBook(@Param("bookId")long bookId, @Param("inventory")int number);

    void udpateBookInventory(@Param("bookId")long bookId, @Param("inventory")int number);

    void udpateBookCurrentInventory(@Param("bookId")long bookId, @Param("inventory")int number);
    List<Book> getBookList(int offset, int size);

    int getTotalCount(int size);

    Book getBookByNameAndAuthor(@Param("bookName") String bookName,@Param("author") String author);
    Book getInactiveBookByNameAndAuthor(@Param("bookName") String bookName,@Param("author") String author);

    int inactiveBook(@Param("inactive")boolean inactive, @Param("bookId")long bookId);

    int deleteBookInventory(long bookId);

}

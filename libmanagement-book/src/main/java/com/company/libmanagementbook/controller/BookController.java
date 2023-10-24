package com.company.libmanagementbook.controller;

import com.company.libmanagementbook.service.impl.BookService;
import com.company.libmanagementbook.entity.Book;
import com.company.libmanagementbook.service.impl.OrderService;
import com.company.libmanagementutils.entity.LibResultInfo;
import com.company.libmanagementutils.utils.HelperUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "book")
@RestController
@RequestMapping(value = "api/v1/book")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    OrderService orderService;

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @RequestMapping(value="new-book",method= RequestMethod.POST)
    public LibResultInfo addBook(String bookName, String author, String number){
        LibResultInfo result = validateInput(bookName,author,number);
        if(!result.isStatus()){
            return result;
        }

        Integer bookNumber = HelperUtil.convertString2Int(number);
        boolean isUpdate = false;
        int bookInventory = 0;
        try{
            Book book = bookService.getBookByNameAndAuthor(bookName, author);
            if (book == null){
                Book inactivebook = bookService.getInactiveBookByNameAndAuthor(bookName, author);
                book = inactivebook == null? new Book(bookName,author): inactivebook;
                Book newBook = bookService.addBook(book,bookNumber);
                bookInventory = newBook.getInventory();

            }else{
                isUpdate = true;
                bookInventory = bookService.updateBook(book, bookNumber).getInventory();
            }
        }catch (Exception ex){
            log.error("add book error : ", ex);
            return new LibResultInfo(false, "Failed to add book.");
        }

        if(isUpdate){
            return new LibResultInfo(true, "Book \"" + bookName + "\" inventory successfully updated, new inventory:" + String.valueOf(bookInventory));

        }else{
            return new LibResultInfo(true, "Book \"" + bookName + "\" by " + author + " added successfully, inventory:" + String.valueOf(bookInventory));
        }
    }

    @RequestMapping(value = "books", method= RequestMethod.GET)
    public List<Book> listBook(int pageNumber, int size){
        int offset = (pageNumber - 1) * size;

        return bookService.getBookList(offset, size);
    }

    @RequestMapping(value = "book", method= RequestMethod.GET)
    public Book searchBook(String bookName, String author){
        if(!validateInput(bookName,author,"1").isStatus()){
            return null;
        }

        return bookService.getBookByNameAndAuthor(bookName, author);
    }

    @RequestMapping(value = "book", method= RequestMethod.POST)
    public LibResultInfo deleteBook(String bookName, String author){
        LibResultInfo result = validateInput(bookName,author,"1");
        if(!result.isStatus()){
            return result;
        }

        Book book = bookService.getBookByNameAndAuthor(bookName, author);
        if(bookName == null){
            return new LibResultInfo(false, "book does not exist.");
        }

        if (orderService.getOrderListbyBookId(book.getBookId()).size() > 0){
            return new LibResultInfo(false, String.format("Cannot delete book \"%s\" because it is currently borrowed", bookName));
        }

        int deleteStatus = -1;
        try{
            deleteStatus = bookService.deleteBook(book.getBookId());
        }catch (Exception ex){
            log.error("add book error : ", ex);
            return new LibResultInfo(false, "Failed to add book.");
        }

        if (deleteStatus == -1){
            return new LibResultInfo(false, String.format("Cannot delete book \"%s\" because it is currently borrowed", bookName));
        }else{
            return new LibResultInfo(true, String.format("Delete book \"%s\" successful.", bookName));
        }

    }

    private LibResultInfo validateInput(String name, String author, String number){
        if (name == null || name.isEmpty()){
            return new LibResultInfo(false, "Please enter the book name");
        }

        if (!HelperUtil.checkBookName(name)){
            return new LibResultInfo(false, "book name only allows letters and spaces");
        }

        if (!HelperUtil.checkName(author)){
            return new LibResultInfo(false, "Author name only allows letters and spaces");
        }
        if (!HelperUtil.checkNumber(number)){
            return new LibResultInfo(false, "inventory number is not correctly");
        }

        return new LibResultInfo(true, "");
    }

}

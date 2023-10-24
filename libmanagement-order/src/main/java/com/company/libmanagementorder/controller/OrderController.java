package com.company.libmanagementorder.controller;

import com.company.libmanagementorder.entity.Book;
import com.company.libmanagementorder.entity.Order;
import com.company.libmanagementutils.entity.LibResultInfo;
import com.company.libmanagementorder.service.impl.BookService;
import com.company.libmanagementorder.service.impl.OrderService;
import com.company.libmanagementutils.entity.SecurityContextUtil;
import com.company.libmanagementutils.utils.HelperUtil;
import com.company.libmanagementutils.utils.IDUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "order")
@RestController
@RequestMapping(value = "api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping(value="new-order", method= RequestMethod.POST)
    public LibResultInfo createOrder(String bookName, String author, boolean isReturn){
        LibResultInfo result = checkBorrowInput(bookName, author);
        if(!result.isStatus()) {
            return result;
        }

        if (isReturn){
            return returnBook(bookName, author);
        }else{
            return borrowBook(bookName, author);
        }

    }

    public LibResultInfo borrowBook(String bookName, String author){
        Book book = bookService.getBookByNameAndAuthor(bookName, author);
        if (book == null){
            return new LibResultInfo(false, "Book does not exist.");
        }
        if (book.getInventory() == 0){
            return new LibResultInfo(false, "Book has no inventory.");
        }

        if(getOrderId(SecurityContextUtil.getUser(), book.getBookId()) != Long.MIN_VALUE) {
            return new LibResultInfo(false, "You have borrowed this book.");
        }

        Order order = new Order(IDUtil.getRandomId(), book.getBookId(), SecurityContextUtil.getUser());
        boolean success =false;
        try{
            success = orderService.addOrder(order);
        }catch (Exception ex){
            log.error("add book error : ", ex);
            return new LibResultInfo(false, "Failed to add book.");
        }

        if(success){
            return new LibResultInfo(true, "Book \""+ bookName + "\" successfully borrowed.");
        }else{
            return new LibResultInfo(false, "Failed to borrow book.");
        }
    }

    public LibResultInfo returnBook(String bookName, String author){


        Book book = bookService.getBookByNameAndAuthor(bookName, author);
        if (book == null){
            return new LibResultInfo(false, "Book does not exist.");
        }

        Long orderId = getOrderId(SecurityContextUtil.getUser(), book.getBookId());
        if(orderId == Long.MIN_VALUE) {
            return new LibResultInfo(false, "You have not borrowed this book.");
        }

        try{
            orderService.returnOrder(orderId);
        }catch (Exception ex){
            log.error(String.format("return book error. name : %s, author %s. %s"), bookName, author, ex.getMessage());
            return new LibResultInfo(false, "Failed to return book.");
        }
        return new LibResultInfo(true, "Book \"" + book.getBookName() + "\" successfully returned.");
    }

    private LibResultInfo checkBorrowInput(String bookName, String author){
        if (bookName == null || bookName.isEmpty()){
            return new LibResultInfo(false, "Please enter the book name");
        }

        if (!HelperUtil.checkBookName(bookName)){
            return new LibResultInfo(false, "book name only allows letters and spaces");
        }

        if (!HelperUtil.checkName(author)){
            return new LibResultInfo(false, "Author name only allows letters and spaces");
        }

        return new LibResultInfo(true,"");
    }

    private Long getOrderId(int userId, long bookId){
        List<Order> borrowList = orderService.getOrderListByUserId(userId);
        for (Order order : borrowList) {
            if (order.getBookId() == bookId){
                return order.getOrderId();
            }
        }

        return Long.MIN_VALUE;
    }
}

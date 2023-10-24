package com.company.libmanagementbook.service.impl;

import com.company.libmanagementbook.entity.Book;
import com.company.libmanagementbook.entity.Order;
import com.company.libmanagementbook.mapper.BookMapper;
import com.company.libmanagementbook.mapper.OrderMapper;
import com.company.libmanagementbook.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    BookMapper bookMapper;

    @Override
    public Order getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Transactional
    @Override
    public boolean addOrder(Order order) {
        Book book = bookMapper.getBook(order.getBookId());
        if(book.getInventory() == 0){
            return false;
        }
        orderMapper.addOrder(order);
        bookMapper.udpateBookCurrentInventory(book.getBookId(), -1);
        return true;
    }

    @Transactional
    @Override
    public void returnOrder(long orderId) {
        Order order = orderMapper.getOrderById(orderId);
        orderMapper.udpateOrder(orderId);
        bookMapper.udpateBookCurrentInventory(order.getBookId(), 1);
    }

    @Override
    public List<Order> getOrderListByUserId(int userId) {
        return orderMapper.getOrderListByUserId(userId);
    }

    @Override
    public List<Order> getOrderListbyBookId(long bookId) {
        return orderMapper.getOrderListbyBookId(bookId);
    }

    @Override
    public Order getOrderByUserAndBook(int userId, long bookId) {
        return orderMapper.getOrderByUserAndBook(userId, bookId);
    }



}


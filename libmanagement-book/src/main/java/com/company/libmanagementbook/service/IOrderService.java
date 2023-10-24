package com.company.libmanagementbook.service;

import com.company.libmanagementbook.entity.Order;

import java.util.List;

public interface IOrderService {
    Order getOrderById(long orderId);

    boolean addOrder(Order order);

    List<Order> getOrderListByUserId(int userId);

    List<Order> getOrderListbyBookId(long bookId);

    Order getOrderByUserAndBook(int userId, long bookId);

    void returnOrder(long orderId);
}

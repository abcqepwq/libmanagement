package com.company.libmanagementorder.mapper;

import com.company.libmanagementorder.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    Order getOrderById(long orderId);

    boolean addOrder(Order order);

    Order getOrderByUserAndBook(int userId, long bookId);

    void udpateOrder(long orderId);

    List<Order> getOrderListByUserId(int userId);

    List<Order> getOrderListbyBookId(long bookId);

}

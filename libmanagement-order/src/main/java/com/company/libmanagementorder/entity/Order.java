package com.company.libmanagementorder.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Order {
    private long orderId;
    private long bookId;
    private int userId;

    public Order(long orderId, long bookId, int userId){
        this.orderId = orderId;
        this.bookId = bookId;
        this.userId = userId;
    }
}

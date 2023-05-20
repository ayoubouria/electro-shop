package com.example.stockmanagement.Cache;

import com.example.stockmanagement.Model.Classes.Order;

public class OrderCache {
    private Order order;
    private final static OrderCache instance = new OrderCache();
    
    private OrderCache() {}

    public static OrderCache getInstance() {
        return instance;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void clearOrder() {
        this.order = null;
    }

    public String toString() {
        return "OrderCache{" + "order=" + order + '}' ;
    }
}

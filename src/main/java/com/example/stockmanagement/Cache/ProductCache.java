package com.example.stockmanagement.Cache;

import com.example.stockmanagement.Model.Classes.Product;

public class ProductCache {
    private Product product;
    private final static ProductCache instance = new ProductCache();

    private ProductCache() {}

    public static ProductCache getInstance() {
        return instance;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public void clearProduct() {
        this.product = null;
    }

    public String toString() {
        return "ProductCache{" + "product=" + product + '}' ;
    }
    
}

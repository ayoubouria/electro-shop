package com.example.stockmanagement.Model.Classes;
import java.util.HashMap;

public class Order {
    int oid;
    int clientId;
    HashMap<Product, Integer> productsList = new HashMap<Product, Integer>();
    String date;
    int total = 0;

    public Order() {}
    public Order(int clientId,HashMap<Product, Integer> productsList ,String date) {
        this.clientId = clientId;
        this.productsList = productsList;
        this.date = date;
        for (Product product : productsList.keySet()) {
            this.total += product.getPrice() * productsList.get(product);
        }
    }
    public Order(int clientId, HashMap<Product, Integer> productsList, String date, int total) {
        this.clientId = clientId;
        this.productsList = productsList;
        this.date = date;
        this.total = total;
    }
    public Order(int oid, int clientId, HashMap<Product, Integer> productsList, String date, int total) {
        this.oid = oid;
        this.clientId = clientId;
        this.productsList = productsList;
        this.date = date;
        this.total = total;
    }


    public int getOid() {
        return this.oid;
    }
    public void setOid(int oid) {
        this.oid = oid;
    }
    public int getClientId() {
        return this.clientId;
    }
    public HashMap<Product, Integer> getProductsList() {
        return this.productsList;
    }
    public String getDate() {
        return this.date;
    }
    public int getTotal() {
        return this.total;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public void setProductsList(HashMap<Product, Integer> productsList) {
        this.productsList = productsList;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public String toString() {
        return "Order [oid=" + oid + ", clientId=" + clientId + ", productsList=" + productsList + ", date=" + date
                + ", total=" + total + "]";
    }

    public void addProduct(Product product, int quantity) {
        this.productsList.put(product, quantity);
    }

    public void addToTotal(int price) {
        this.total += price;
    }

}

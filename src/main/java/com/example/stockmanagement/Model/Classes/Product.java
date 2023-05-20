package com.example.stockmanagement.Model.Classes;



public class Product {
    int pid;
    String name;
    String code;
    String category;
    int price;
    int quantity;
    String position;


    public Product() {}
    public Product(String name, String code, String category, int price, int quantity, String position) {
        this.name = name;
        this.code = code;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.position = position;
    }

    public Product(int pid, String name, String code, String category, int price, int quantity, String position) {
        this.pid = pid;
        this.name = name;
        this.code = code;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.position = position;
    }

    public int getPid() {
        return this.pid;
    }
    public String getName() {
        return this.name;
    }
    public String getCode() {
        return this.code;
    }
    public String getCategory() {
        return this.category;
    }
    public int getPrice() {
        return this.price;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public String getPosition() {
        return this.position;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String toString() {
        return name ;
    }

 

}

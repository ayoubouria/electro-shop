package com.example.stockmanagement.Model.Classes;

public class Client {
    int cid;
    String name;
    String email;
    String phone;
    String address;

    public Client() {}

    public Client(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Client(int cid, String name, String email, String phone, String address) {
        this.cid = cid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getCid() {
        return this.cid;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getAddress() {
        return this.address;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
}

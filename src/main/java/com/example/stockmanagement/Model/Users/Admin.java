package com.example.stockmanagement.Model.Users;

public class Admin  extends User{
    public Admin() {}
    public Admin(String username, String password, String firstName, String lastName, String registrationDate) {
        super(username, password, firstName, lastName, registrationDate);
        this.isAdmin = true;
    }
}

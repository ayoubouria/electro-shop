package com.example.stockmanagement.Model.Users;

public class User {
    int id;
    String username;
    String password;
    String firstName;
    String lastName;
    boolean isAdmin;
    String dateCreated;

    public User() {}
    public User(String username, String password, String firstName, String lastName, String dateCreated) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = false;
        this.dateCreated = dateCreated;
    }
    public User(int id, String username, String password, String firstName, String lastName, boolean isAdmin, String dateCreated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return this.id;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public boolean getIsAdmin() {
        return this.isAdmin;
    }
    public String getDateCreated() {
        return this.dateCreated;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}

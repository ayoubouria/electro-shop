package com.example.stockmanagement.Cache;

import com.example.stockmanagement.Model.Users.User;

public class UserCache {
    private User user;
    private final static UserCache instance = new UserCache();

    private UserCache() {}

    public static UserCache getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void clearUser() {
        this.user = null;
    }

    public String toString() {
        return "UserCache{" + "user=" + user + '}' ;
    }
}

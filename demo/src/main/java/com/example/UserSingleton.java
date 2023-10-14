package com.example;

public class UserSingleton {
    private static UserSingleton instance = new UserSingleton();
    private int userId;
    
    private UserSingleton() {}

    public static UserSingleton getInstance() {
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId; 
    }
}

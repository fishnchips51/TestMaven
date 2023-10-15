package com.example.Singletons;

public class ClientSingleton {
    private static ClientSingleton instance = new ClientSingleton();
    private int clientId;
    
    public static ClientSingleton getInstance() {
        return instance;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}

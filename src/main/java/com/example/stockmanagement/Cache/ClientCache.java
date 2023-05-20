package com.example.stockmanagement.Cache;

import com.example.stockmanagement.Model.Classes.Client;

public class ClientCache {
    private Client client;
    private final static ClientCache instance = new ClientCache();

    private ClientCache() {}

    public static ClientCache getInstance() {
        return instance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void clearClient() {
        this.client = null;
    }

    public String toString() {
        return "ClientCache{" + "client=" + client + '}' ;
    }
}

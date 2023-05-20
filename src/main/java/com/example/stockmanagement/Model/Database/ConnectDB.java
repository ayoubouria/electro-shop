package com.example.stockmanagement.Model.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private String url = "";
    private String dbName = "";
    private String login = "";
    private String password = "";


    public ConnectDB(){}

    public String getUrl() {
        return url;
    }
    public String getDbName() {
        return dbName;
    }
    public String getLogin() {
        return login;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(this.url + this.dbName, this.login, this.password);
    }

}

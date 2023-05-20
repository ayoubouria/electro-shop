package com.example.stockmanagement.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.stockmanagement.Model.Classes.Client;

public class ClientsDB {
    public ArrayList<Client> selectAllClients() throws SQLException {
        ConnectDB db = new ConnectDB();
        ArrayList<Client> clients = new ArrayList<Client>();
        Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM clients";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Client client = new Client(rs.getInt("cid"), rs.getString("cli_name"),
                                        rs.getString("cli_email"), rs.getString("cli_phone"), 
                                        rs.getString("cli_add"));
            clients.add(client);
        }
        return clients;
    }

    public Client selectClient(int cid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM clients WHERE cid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, cid);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Client client = new Client(rs.getInt("cid"), rs.getString("cli_name"),
                                        rs.getString("cli_email"), rs.getString("cli_phone"), 
                                        rs.getString("cli_add"));
            return client;
        }
        return null;
    }
    public Client selectClient(String clientName) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM clients WHERE cli_name = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, clientName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Client client = new Client(rs.getInt("cid"), rs.getString("cli_name"),
                                        rs.getString("cli_email"), rs.getString("cli_phone"), 
                                        rs.getString("cli_add"));
            return client;
        }
        return null;
    }

    public void insertClient(Client client) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "INSERT INTO clients (cli_name, cli_email, cli_phone, cli_add) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, client.getName());
        stmt.setString(2, client.getEmail());
        stmt.setString(3, client.getPhone());
        stmt.setString(4, client.getAddress());
        stmt.executeUpdate();
    }

    public void updateClient(Client client) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "UPDATE clients SET cli_name = ?, cli_email = ?, cli_phone = ?, cli_add = ? WHERE cid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, client.getName());
        stmt.setString(2, client.getEmail());
        stmt.setString(3, client.getPhone());
        stmt.setString(4, client.getAddress());
        stmt.setInt(5, client.getCid());
        stmt.executeUpdate();
    }

    public void deleteClient(int cid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "DELETE FROM clients WHERE cid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, cid);
        stmt.executeUpdate();
    }
    public int clientCount() throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(*) FROM clients";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int getClientId(String name) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT cid FROM clients WHERE cli_name = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("cid");
        }
        return 0;
    }
    public String getClientName(int cid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT cli_name FROM clients WHERE cid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, cid);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("cli_name");
        }
        return null;
    }
}

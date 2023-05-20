package com.example.stockmanagement.Model.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.stockmanagement.Model.Users.User;

public class UserDB {
    public ArrayList<User> selectAllUsers() throws SQLException {
        ConnectDB db = new ConnectDB();
        ArrayList<User> users = new ArrayList<User>();
        Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM users";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            User user = new User(rs.getInt("uid"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"), rs.getBoolean("is_admin"), rs.getString("date_created"));
            users.add(user);
        }
        return users;
    }

    public User selectUser(int uid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM users WHERE uid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, uid);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new User(rs.getInt("uid"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"), rs.getBoolean("is_admin"), rs.getString("date_created"));
            return user;
        }
        return null;
    }

    public User selectUser(String username) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new User(rs.getInt("uid"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"), rs.getBoolean("is_admin"), rs.getString("date_created"));
            return user;
        }
        return null;
    }
    public User selectUser(String username, String password) throws SQLException
    {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM users WHERE username = ? AND password = SHA2(?, 256)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            User user = new User(rs.getInt("uid"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"), rs.getBoolean("is_admin"), rs.getString("date_created"));
            return user;
        }
        return null;
    }

    public void insertUser(User user) throws SQLException
    {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "INSERT INTO users (username, password, first_name, last_name, is_admin, date_created) VALUES (?, SHA2(?, 256), ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getFirstName());
        stmt.setString(4, user.getLastName());
        stmt.setBoolean(5, user.getIsAdmin());
        stmt.setString(6, user.getDateCreated());
        stmt.executeUpdate();
    }
    public void deleteUser(User user) throws SQLException
    {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "DELETE FROM users WHERE uid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, user.getId());
        stmt.executeUpdate();
    }
    public void updateUser(User user) throws SQLException
    {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "UPDATE users SET username = ?, password = SHA2(?, 256), first_name = ?, last_name = ?, is_admin = ?, date_created = ? WHERE uid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getFirstName());
        stmt.setString(4, user.getLastName());
        stmt.setBoolean(5, user.getIsAdmin());
        stmt.setString(6, user.getDateCreated());
        stmt.setInt(7, user.getId());
        stmt.executeUpdate();
    }



}

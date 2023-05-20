package com.example.stockmanagement.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.stockmanagement.Model.Classes.Product;

public class ProductDB {
    public ArrayList<Product> selectAllProducts() throws SQLException {
        ConnectDB db = new ConnectDB();
        ArrayList<Product> products = new ArrayList<Product>();
        Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM products";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Product product = new Product(rs.getInt("pid"), rs.getString("prod_name"),
                    rs.getString("prod_code"), rs.getString("category"),
                    rs.getInt("prod_price"), rs.getInt("prod_qty"),
                    rs.getString("prod_pos"));
            products.add(product);
        }
        return products;
    }

    public Product selectProduct(int pid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM products WHERE pid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, pid);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt("pid"), rs.getString("prod_name"),
                    rs.getString("prod_code"), rs.getString("category"),
                    rs.getInt("prod_price"), rs.getInt("prod_qty"),
                    rs.getString("prod_pos"));
            return product;
        }
        return null;
    }

    public ArrayList<Product> selectProductsByCategory(String cat) throws SQLException {
        ConnectDB db = new ConnectDB();
        ArrayList<Product> products = new ArrayList<Product>();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM products WHERE category = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, cat);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Product product = new Product(rs.getInt("pid"), rs.getString("prod_name"),
                    rs.getString("prod_code"), rs.getString("category"),
                    rs.getInt("prod_price"), rs.getInt("prod_qty"),
                    rs.getString("prod_pos"));
            products.add(product);
        }
        return products;
    }

    public ArrayList<Product> selectProductsByPosition(String pos) throws SQLException {
        ConnectDB db = new ConnectDB();
        ArrayList<Product> products = new ArrayList<Product>();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM products WHERE prod_pos = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, pos);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Product product = new Product(rs.getInt("pid"), rs.getString("prod_name"),
                    rs.getString("prod_code"), rs.getString("category"),
                    rs.getInt("prod_price"), rs.getInt("prod_qty"),
                    rs.getString("prod_pos"));
            products.add(product);
        }
        return products;
    }

    public void insertProduct(Product product) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "INSERT INTO products (prod_name, prod_code, category, prod_price, prod_qty, prod_pos) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getCode());
        stmt.setString(3, product.getCategory());
        stmt.setInt(4, product.getPrice());
        stmt.setInt(5, product.getQuantity());
        stmt.setString(6, product.getPosition());
        stmt.executeUpdate();
    }

    public void updateProduct(Product product) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "UPDATE products SET prod_name = ?, prod_code = ?, category = ?, prod_price = ?, prod_qty = ?, prod_pos = ? WHERE pid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getCode());
        stmt.setString(3, product.getCategory());
        stmt.setInt(4, product.getPrice());
        stmt.setInt(5, product.getQuantity());
        stmt.setString(6, product.getPosition());
        stmt.setInt(7, product.getPid());
        stmt.executeUpdate();
    }

    public void deleteProduct(int pid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "DELETE FROM products WHERE pid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, pid);
        stmt.executeUpdate();
    }

    public boolean deleteQuantity(int pid, int quantity) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        int newQuantity = 0;
        newQuantity = selectProduct(pid).getQuantity() - quantity;
        if (newQuantity < 0) {
            return false;
        }
        String query = "UPDATE products SET prod_qty = ? WHERE pid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, newQuantity);
        stmt.setInt(2, pid);
        stmt.executeUpdate();
        return true;
    }
    public int productCount() throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT COUNT(*) FROM products";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    public int productCountByCategory(String cat) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT COUNT(*) FROM products WHERE category = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, cat);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }


}

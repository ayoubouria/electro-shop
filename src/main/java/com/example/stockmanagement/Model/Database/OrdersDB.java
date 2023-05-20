package com.example.stockmanagement.Model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.stockmanagement.Model.Classes.Product;
import com.example.stockmanagement.Model.Classes.Order;

public class OrdersDB {
    private ResultSet getOrderDetails(int order_id) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM order_details WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, order_id);
        ResultSet rs = stmt.executeQuery();
        return rs;
    }

    public ArrayList<Order> selectAllOrders() throws SQLException {
        ConnectDB db = new ConnectDB();
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM orders";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            // System.out.println("Order ID: " + rs.getInt("oid") + " Client ID: " +
            // rs.getInt("cid") + " Order Date: " + rs.getString("order_date"));
            Order order = new Order();
            order.setOid(rs.getInt("oid"));
            order.setClientId(rs.getInt("cid"));
            order.setDate(rs.getString("order_date"));
            ResultSet rs2 = getOrderDetails(rs.getInt("oid"));
            while (rs2.next()) {
                // System.out.println("Product ID: " + rs2.getInt("product_id") + " Order Qty: "
                // + rs2.getInt("order_qty") + " Order Price: " + rs2.getInt("order_price"));
                ProductDB pdb = new ProductDB();
                Product product = pdb.selectProduct(rs2.getInt("product_id"));
                order.addProduct(product, rs2.getInt("order_qty"));
                order.addToTotal(rs2.getInt("order_price"));
            }
            orders.add(order);
        }
        return orders;
    }

    public Order selectOrder(int oid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM orders WHERE oid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, oid);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Order order = new Order();
            order.setOid(rs.getInt("oid"));
            order.setClientId(rs.getInt("cid"));
            order.setDate(rs.getString("order_date"));
            ResultSet rs2 = getOrderDetails(rs.getInt("oid"));
            while (rs2.next()) {
                ProductDB pdb = new ProductDB();
                Product product = pdb.selectProduct(rs2.getInt("product_id"));
                order.addProduct(product, rs2.getInt("order_qty"));
                order.addToTotal(rs2.getInt("order_price"));
            }
            return order;
        }
        return null;
    }

    private int getLastOrderId() throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT MAX(oid) FROM orders";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void insertOrder(Order order) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "INSERT INTO orders (cid, order_date) VALUES (?, DATE (?) )";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, order.getClientId());
        stmt.setString(2, order.getDate());
        stmt.executeUpdate();
        int oid = getLastOrderId();
        String query2 = "INSERT INTO order_details (order_id, product_id, order_qty, order_price) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt2 = conn.prepareStatement(query2);
        for (Product i : order.getProductsList().keySet()) {
            stmt2.setInt(1, oid);
            stmt2.setInt(2, i.getPid());
            stmt2.setInt(3, order.getProductsList().get(i));
            stmt2.setInt(4, i.getPrice() * order.getProductsList().get(i));
            stmt2.executeUpdate();
        }
        insertConfirmOrder(order.getOid());
    }

    private void deleteOrderDetails(int oid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "DELETE FROM order_details WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, oid);
        stmt.executeUpdate();
    }

    public void deleteOrder(int oid) throws SQLException {
        deleteOrderDetails(oid);
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "DELETE FROM orders WHERE oid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, oid);
        stmt.executeUpdate();
        deleteConfirmOrder(oid);
    }

    public void updateOrder(Order order) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "UPDATE orders SET cid = ?, order_date = ? WHERE oid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, order.getClientId());
        stmt.setString(2, order.getDate());
        stmt.setInt(3, order.getOid());
        stmt.executeUpdate();
        String query2 = "DELETE FROM order_details WHERE order_id = ?";
        PreparedStatement stmt2 = conn.prepareStatement(query2);
        stmt2.setInt(1, order.getOid());
        stmt2.executeUpdate();
        String query3 = "INSERT INTO order_details (order_id, product_id, order_qty, order_price) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt3 = conn.prepareStatement(query3);
        for (Product i : order.getProductsList().keySet()) {
            stmt3.setInt(1, order.getOid());
            stmt3.setInt(2, i.getPid());
            stmt3.setInt(3, order.getProductsList().get(i));
            stmt3.setInt(4, i.getPrice() * order.getProductsList().get(i));
            stmt3.executeUpdate();
        }
    }

    public ArrayList<Order> selectClientOrders(int cid) throws SQLException {
        ConnectDB db = new ConnectDB();
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM orders WHERE cid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, cid);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Order order = new Order();
            order.setOid(rs.getInt("oid"));
            order.setClientId(rs.getInt("cid"));
            order.setDate(rs.getString("order_date"));
            ResultSet rs2 = getOrderDetails(rs.getInt("oid"));
            while (rs2.next()) {
                ProductDB pdb = new ProductDB();
                Product product = pdb.selectProduct(rs2.getInt("product_id"));
                order.addProduct(product, rs2.getInt("order_qty"));
                order.addToTotal(rs2.getInt("order_price"));
            }
            orders.add(order);
        }
        return orders;
    }

    public int ordersCount() throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT COUNT(*) FROM orders";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    private void insertConfirmOrder(int oid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "INSERT INTO confirm_order (order_id) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, oid);
        stmt.executeUpdate();
    }

    private void deleteConfirmOrder(int oid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "DELETE FROM confirm_order WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, oid);
        stmt.executeUpdate();
    }

    public boolean checkConfirmOrder(int oid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "SELECT * FROM confirm_order WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, oid);
        ResultSet rs = stmt.executeQuery();
        if (rs.next() && rs.getInt("is_confirmed") == 1) {
            return true;
        }
        return false;
    }
    public void confirmOrder(int oid) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "UPDATE confirm_order SET is_confirmed = 1 WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, oid);
        stmt.executeUpdate();
    }
    public void delteProductQuantity(Order order) throws SQLException {
        ConnectDB db = new ConnectDB();
        Connection conn = db.getConnection();
        String query = "UPDATE products SET prod_qty = prod_qty - ? WHERE pid = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        for (Product i : order.getProductsList().keySet()) {
            stmt.setInt(1, order.getProductsList().get(i));
            stmt.setInt(2, i.getPid());
            stmt.executeUpdate();
        }
    }

}

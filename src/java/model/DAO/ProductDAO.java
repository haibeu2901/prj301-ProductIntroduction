/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DTO.Account;
import model.DTO.Category;
import model.DTO.Product;
import utilities.ConnectDb;

/**
 *
 * @author beu29
 */
public class ProductDAO implements Accessible<Product> {

    @Override
    public boolean insertRec(Product o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "INSERT INTO products (productId, productName, productImage, brief, postedDate, typeId, account, unit, price, discount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setString(1, o.getProductId());
            cmd.setString(2, o.getProductName());
            cmd.setString(3, o.getProductImage());
            cmd.setString(4, o.getBrief());
            cmd.setDate(5, o.getPostedDate());
            cmd.setInt(6, o.getType().getTypeId());
            cmd.setString(7, o.getAccount().getAccount());
            cmd.setString(8, o.getUnit());
            cmd.setInt(9, o.getPrice());
            cmd.setInt(10, o.getDiscount());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public boolean updateRec(Product o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "UPDATE products SET productName=?, productImage=?, brief=?, postedDate=?, typeId=?, account=?, unit=?, price=?, discount=? WHERE productId=?;";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setString(1, o.getProductName());
            cmd.setString(2, o.getProductImage());
            cmd.setString(3, o.getBrief());
            cmd.setDate(4, o.getPostedDate());
            cmd.setInt(5, o.getType().getTypeId());
            cmd.setString(6, o.getAccount().getAccount());
            cmd.setString(7, o.getUnit());
            cmd.setInt(8, o.getPrice());
            cmd.setInt(9, o.getDiscount());
            cmd.setString(10, o.getProductId());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public boolean deleteRec(Product o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "DELETE FROM products WHERE productId = ?;";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setString(1, o.getProductId());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public Product getObjectById(String id) {
        Product pro = null;
        for (Product x : listAll()) {
            if (x.getProductId().equals(id)) {
                pro = x;
            }
        }
        return pro;
    }

    @Override
    public List<Product> listAll() {
        List<Product> ls = new ArrayList<>();
        try {
            //--- B1
            Connection c = new ConnectDb().getConnection();

            //--- B2
            String sqlString = "SELECT productId, productName, productImage, brief, postedDate, typeId, account, unit, price, discount FROM products";
            Statement cmd = c.createStatement();

            //--- B3
            ResultSet rs = cmd.executeQuery(sqlString);

            //--- B4
            while (rs.next()) {
                //--- B4.1
                String id = rs.getString("productId");
                String name = rs.getString("productName");
                String image = rs.getString("productImage");
                String brief = rs.getString("brief");
                Date pDate = rs.getDate("postedDate");
                int typeId = rs.getInt("typeId");
                Category type = new CategoryDAO().getObjectById(String.valueOf(typeId));
                String acc = rs.getString("account");
                Account account = new AccountDAO().getObjectById(acc);
                String unit = rs.getString("unit");
                int price = rs.getInt("price");
                int discount = rs.getInt("discount");
                //--- B4.2
                Product pro = new Product(id, name, image, brief, pDate, type, account, unit, price, discount);
                //--- B4.3
                ls.add(pro);
            }
            //--- B5
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public List<Product> searchProducts(String search) {
        List<Product> ls = new ArrayList<>();
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "SELECT productId, productName, productImage, brief, postedDate, typeId, account, unit, price, discount FROM products WHERE productname LIKE ?;";
            PreparedStatement cmd = c.prepareCall(sqlString);
            cmd.setString(1, "%" + search + "%");
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                String id = rs.getString("productId");
                String name = rs.getString("productName");
                String image = rs.getString("productImage");
                String brief = rs.getString("brief");
                Date pDate = rs.getDate("postedDate");
                int typeId = rs.getInt("typeId");
                Category type = new CategoryDAO().getObjectById(String.valueOf(typeId));
                String acc = rs.getString("account");
                Account account = new AccountDAO().getObjectById(acc);
                String unit = rs.getString("unit");
                int price = rs.getInt("price");
                int discount = rs.getInt("discount");

                Product pro = new Product(id, name, image, brief, pDate, type, account, unit, price, discount);

                ls.add(pro);
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public List<Product> sortProduct(String priceRange, int minPrice, int maxPrice, String productName, String hasDiscount, int catId) {
        List<Product> ls = new ArrayList<>();
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "SELECT productId, productName, productImage, brief, postedDate, typeId, account, unit, price, discount FROM products WHERE 1=1";
            // 🔹 Price Range Filter
            if (minPrice > 0) {
                sqlString += " AND price >= " + minPrice;
            }
            if (maxPrice > 0 && maxPrice >= minPrice && maxPrice != Integer.MAX_VALUE) {
                sqlString += " AND price <= " + maxPrice;
            }

            // 🔹 Product's name Filter
            if (productName != null && !productName.trim().isEmpty()) {
                sqlString += " AND productName LIKE N'%" + productName.replace("'", "''") + "%'"; // Prevent SQL injection
            }

            // 🔹 Discount Filter
            if ("yes".equals(hasDiscount)) {
                sqlString += " AND discount > 0";
            } else if ("no".equals(hasDiscount)) {
                sqlString += " AND discount = 0";
            }

            // 🔹 Category Filter
            if (catId != 0) {
                sqlString += " AND typeId = " + catId;
            }

            // 🔹 Sorting
            if ("high".equals(priceRange)) {
                sqlString += " ORDER BY price DESC ";
            } else if ("no".equals(priceRange)) {
                sqlString += " ORDER BY price ASC ";
            }

            Statement cmd = c.createStatement();
            ResultSet rs = cmd.executeQuery(sqlString);
            while (rs.next()) {
                String id = rs.getString("productId");
                String name = rs.getString("productName");
                String image = rs.getString("productImage");
                String brief = rs.getString("brief");
                Date pDate = rs.getDate("postedDate");
                int typeId = rs.getInt("typeId");
                Category type = new CategoryDAO().getObjectById(String.valueOf(typeId));
                String acc = rs.getString("account");
                Account account = new AccountDAO().getObjectById(acc);
                String unit = rs.getString("unit");
                int price = rs.getInt("price");
                int discount = rs.getInt("discount");

                Product pro = new Product(id, name, image, brief, pDate, type, account, unit, price, discount);

                ls.add(pro);
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

}

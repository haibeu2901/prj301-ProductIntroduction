/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DTO.Category;
import utilities.ConnectDb;

/**
 *
 * @author beu29
 */
public class CategoryDAO implements Accessible<Category> {

    @Override
    public boolean insertRec(Category o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "INSERT INTO categories (categoryName, memo) VALUES (?, ?);";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setString(1, o.getCategoryName());
            cmd.setString(2, o.getMemo());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public boolean updateRec(Category o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "UPDATE categories SET CategoryName=?, memo=? WHERE typeId=?;";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setString(1, o.getCategoryName());
            cmd.setString(2, o.getMemo());
            cmd.setInt(3, o.getTypeId());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public boolean deleteRec(Category o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "DELETE FROM categories WHERE typeId = ?;";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setInt(1, o.getTypeId());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public Category getObjectById(String id) {
        Category cat = null;
        for (Category x : listAll()) {
            if (String.valueOf(x.getTypeId()).equals(id)) {
                cat = x;
            }
        }
        return cat;
    }

    @Override
    public List<Category> listAll() {
        List<Category> ls = new ArrayList<>();
        try {
            //--- B1
            Connection c = new ConnectDb().getConnection();

            //--- B2
            String sqlString = "SELECT typeId, categoryName, memo FROM categories";
            Statement cmd = c.createStatement();

            //--- B3
            ResultSet rs = cmd.executeQuery(sqlString);

            //--- B4
            while (rs.next()) {
                //--- B4.1
                int id = rs.getInt("typeId");
                String name = rs.getString("categoryName");
                String memo = rs.getString("memo");

                //--- B4.2
                Category cat = new Category(id, name, memo);

                //--- B4.3
                ls.add(cat);
            }
            //--- B5
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public List<Category> sortCategory(String typeId, String categoryName) {
        List<Category> ls = new ArrayList<>();
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "SELECT typeId, categoryName, memo FROM categories WHERE 1=1";
            // 🔹 type Id Filter
            if (typeId!=null && !typeId.trim().isEmpty()) {
                int catId = Integer.parseInt(typeId);
                sqlString += " AND typeId = " + catId;
            }

            // 🔹 category's name Filter
            if (categoryName != null && !categoryName.trim().isEmpty()) {
                sqlString += " AND categoryName LIKE N'%" + categoryName.replace("'", "''") + "%'"; // Prevent SQL injection
            }

            Statement cmd = c.createStatement();
            ResultSet rs = cmd.executeQuery(sqlString);
            while (rs.next()) {
                int id = rs.getInt("typeId");
                String name = rs.getString("categoryName");
                String memo = rs.getString("memo");

                Category cat = new Category(id, name, memo);

                ls.add(cat);
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

}

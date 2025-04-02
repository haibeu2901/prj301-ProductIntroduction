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
import utilities.ConnectDb;

/**
 *
 * @author beu29
 */
public class AccountDAO implements Accessible<Account> {

    @Override
    public boolean insertRec(Account o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "INSERT INTO accounts (account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setString(1, o.getAccount());
            cmd.setString(2, o.getPass());
            cmd.setString(3, o.getLastName());
            cmd.setString(4, o.getFirstName());
            cmd.setDate(5, o.getBirthday());
            cmd.setBoolean(6, o.isGender());
            cmd.setString(7, o.getPhone());
            cmd.setBoolean(8, o.isIsUse());
            cmd.setInt(9, o.getRoleInSystem());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public boolean updateRec(Account o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "UPDATE accounts SET pass=?, lastName=?, firstName=?, birthday=?, gender=?, phone=?, isUse=?, roleInSystem=? WHERE account=?;";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setString(1, o.getPass());
            cmd.setString(2, o.getLastName());
            cmd.setString(3, o.getFirstName());
            cmd.setDate(4, o.getBirthday());
            cmd.setBoolean(5, o.isGender());
            cmd.setString(6, o.getPhone());
            cmd.setBoolean(7, o.isIsUse());
            cmd.setInt(8, o.getRoleInSystem());
            cmd.setString(9, o.getAccount());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public boolean deleteRec(Account o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "DELETE FROM accounts WHERE account = ?;";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setString(1, o.getAccount());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    @Override
    public Account getObjectById(String id) {
        Account acc = null;
        for (Account x : listAll()) {
            if (x.getAccount().equals(id)) {
                acc = x;
            }
        }
        return acc;
    }

    @Override
    public List<Account> listAll() {
        List<Account> ls = new ArrayList<>();
        try {
            //--- B1
            Connection c = new ConnectDb().getConnection();

            //--- B2
            String sqlString = "SELECT account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem FROM accounts";
            Statement cmd = c.createStatement();

            //--- B3
            ResultSet rs = cmd.executeQuery(sqlString);

            //--- B4
            while (rs.next()) {
                //--- B4.1
                String account = rs.getString("account");
                String pass = rs.getString("pass");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                Date birthday = rs.getDate("birthday");
                boolean gender = rs.getBoolean("gender");
                String phone = rs.getString("phone");
                boolean isUse = rs.getBoolean("isUse");
                int roleInSystem = rs.getInt("roleInSystem");
                //--- B4.2
                Account acc = new Account(account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem);
                //--- B4.3
                ls.add(acc);
            }
            //--- B5
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    public Account getUserByLogin(String account, String pass) {

        Account kq = null;

        try {
            Connection c = new ConnectDb().getConnection();

            String sqlString = "SELECT account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem FROM accounts WHERE account = '" + account + "' AND pass = '" + pass + "';";
            Statement cmd = c.createStatement();

            ResultSet rs = cmd.executeQuery(sqlString);

            while (rs.next() && kq == null) {
                String accId = rs.getString("account");
                String pwd = rs.getString("pass");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                Date birthday = rs.getDate("birthday");
                boolean gender = rs.getBoolean("gender");
                String phone = rs.getString("phone");
                boolean isUse = rs.getBoolean("isUse");
                int roleInSystem = rs.getInt("roleInSystem");

                kq = new Account(accId, pwd, lastName, firstName, birthday, gender, phone, isUse, roleInSystem);
            }

            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kq;

    }

    public boolean updateAccountStatus(Account o) {
        boolean kq = false;
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "UPDATE accounts SET isUse=? WHERE account=?;";
            PreparedStatement cmd = c.prepareStatement(sqlString);
            cmd.setBoolean(1, !o.isIsUse());
            cmd.setString(2, o.getAccount());
            int rs = cmd.executeUpdate();
            kq = (rs > 0);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    public List<Account> sortAccount(String accountId, String fullName, String gend, String phoneNum, String isUsed, String role) {
        List<Account> ls = new ArrayList<>();
        try {
            Connection c = new ConnectDb().getConnection();
            String sqlString = "SELECT account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem FROM accounts WHERE 1=1";
            // 🔹 accountId Filter
            if (accountId != null && !accountId.trim().isEmpty()) {
                sqlString += " AND account LIKE N'%" + accountId.replace("'", "''") + "%'";
            }

            // 🔹 full name Filter
            if (fullName != null && !fullName.trim().isEmpty()) {
                sqlString += " AND (CONCAT(lastName, ' ', firstName) LIKE N'%" + fullName.replace("'", "''") + "%' OR CONCAT(firstName, ' ', lastName) LIKE N'%" + fullName.replace("'", "''") + "%')"; //Added OR statement
            }

            // 🔹 gender Filter
            if (gend != null && !gend.trim().isEmpty()) {
                boolean sex = "male".equalsIgnoreCase(gend); //Fixed gender logic
                sqlString += " AND gender = " + (sex ? 1 : 0); // Corrected boolean handling
            }

            // 🔹 phone Filter
            if (phoneNum != null && !phoneNum.trim().isEmpty()) {
                sqlString += " AND phone LIKE N'%" + phoneNum.replace("'", "''") + "%'";
            }

            // 🔹 isUse filter
            if (isUsed != null && !isUsed.trim().isEmpty()) {
                boolean use = "true".equalsIgnoreCase(isUsed); //Fixed isUsed logic
                sqlString += " AND isUse = " + (use ? 1 : 0); // Corrected boolean handling
            }

            // 🔹 role filter
            if (role != null && !role.trim().isEmpty()) {
                int roleIS = Integer.parseInt(role);
                sqlString += " AND roleInSystem = " + roleIS;
            }

            Statement cmd = c.createStatement();
            ResultSet rs = cmd.executeQuery(sqlString);
            while (rs.next()) {
                String account = rs.getString("account");
                String pass = rs.getString("pass");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                Date birthday = rs.getDate("birthday");
                boolean gender = rs.getBoolean("gender");
                String phone = rs.getString("phone");
                boolean isUse = rs.getBoolean("isUse");
                int roleInSystem = rs.getInt("roleInSystem");

                Account acc = new Account(account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem);

                ls.add(acc);
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ls;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author beu29
 */
public class ConnectDb {

    private String host;
    private String instance;
    private String port;
    private String database;
    private String user;
    private String pass;

    public ConnectDb() {
        this.host = "127.0.0.1";
        this.instance = "BODUA_GROUP\\BODUAGROUP";
        this.port = "1433";
        this.database = "ProductIntro";
        this.user = "sa";
        this.pass = "12345";
    }
    
    public ConnectDb(ServletContext sc) {
        this.host = sc.getInitParameter("host");
        this.instance = sc.getInitParameter("instance");
        this.port = sc.getInitParameter("port");
        this.database = sc.getInitParameter("database");
        this.user = sc.getInitParameter("user");
        this.pass = sc.getInitParameter("pass");
    }

    public String getUrlConnect() {
        return String.format("jdbc:sqlserver://%s\\%s:%s;databaseName=%s;username=%s;password=%s;useUnicode=true&characterEncoding=UTF-8;",
                this.host,this.instance.trim(), this.port, this.database, this.user, this.pass);
    }

    public Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(getUrlConnect());
//            System.out.println("Ket noi database thanh cong");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

}

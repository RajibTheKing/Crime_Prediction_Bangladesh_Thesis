/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShowCrimeMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Rajib
 */
public class DatabaseConnection {
    
    public String url, dbName, driver, userName, password;
     Connection conn;

    public DatabaseConnection() {
        url = "jdbc:mysql://localhost:3306/";
        dbName = "CrimeRanking";
        driver = "com.mysql.jdbc.Driver";
        userName = "root";
        password = "";
        
    }
    
    public void createConnection(){
        try {
            Class.forName(driver).newInstance();
            conn=DriverManager.getConnection(url + dbName+"?useUnicode=true&characterEncoding=utf-8", userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Oppps");
        }
    }
    
    public void queryInsert(String query) throws SQLException
    {
        createConnection();
        Statement st = conn.createStatement();
        System.out.println(query);
        st.executeUpdate(query);
        closeConnection();
    }
    
    public ResultSet queryResult(String query) throws SQLException
    {
        createConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
//        conn.close();
        return rs;
    }
    public void closeConnection() throws SQLException{
        conn.close();
    }
}

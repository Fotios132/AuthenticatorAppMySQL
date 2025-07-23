/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authenticatorapp;

import java.sql.*;

public class MySQL {

    // MySQL information
    private static final String DB_URL = "jdbc:mysql://localhost:3306/authenticator";
     // since Mahsa doing mySQL all will be in hers   
    private static final String DB_USER = "put your usernamne";  
    private static final String DB_PASSWORD = "put password";  

    // Makes table
    public static void createUsersTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (email VARCHAR(255) PRIMARY KEY, password VARCHAR(255))";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add user, returns true if success
    public static boolean insertUser(String email, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users(email, password) VALUES (?, ?)")) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
   // probably user exists so why worry
            return false;
            
      }
        
        
    }

    
    // Check if user with email and pass exists or not
    public static boolean checkUser(String email, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?"))
        {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            boolean found = rs.next();
            rs.close();
            return found;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

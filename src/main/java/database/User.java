package database;

import static database.Connect.getConnection;
import database.log.Log;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.joda.time.DateTime;

import org.joda.time.format.DateTimeFormat;

public class User {

    public static boolean createUser(String username, String password) throws SQLException, IOException {

        Connection con = getConnection();

        DateTime date = utils.Util.getLocalDate();

        if (userExists(username)) {
            return false;
        }

        String insert = "insert into users(username,password,date_registered) values(?,?,'" + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").print(date) + "');";

        PreparedStatement pstmt = con.prepareStatement(insert);

        String hashedPassword = utils.Util.generateHash(password);

        pstmt.setString(1, username);
        pstmt.setString(2, hashedPassword);

      
        pstmt.executeUpdate();

        con.close();
        return true;

    }

    public static boolean userExists(String username) throws SQLException, IOException {

        Connection con = database.Connect.getConnection();

        Statement s = con.createStatement();

        ResultSet rs = s.executeQuery("select username from users");

        while (rs.next()) {
            String tmp_user = rs.getString("username");
            if (tmp_user.equals(username)) {
                return true;
            }

        }

        con.close();
        return false;

    }

    public static boolean authenticateUser(String username, String password) throws SQLException, IOException {
        
        Connection con = database.Connect.getConnection();

        String query = "select username,password from users where username=?";
        
        PreparedStatement pstmt = con.prepareStatement(query);

        pstmt.setString(1, username);
        
        

        ResultSet rs = pstmt.executeQuery();
        
        String hashedPassword = utils.Util.generateHash(password);
        
        while(rs.next()){
            
            if(rs.getString("password").equals(hashedPassword)){
                return true;
            }
            
        }
        
        return false;
        
    }
    
    
    public static ArrayList<String> getCourses(String username){
        
        
        return null;
    }

}

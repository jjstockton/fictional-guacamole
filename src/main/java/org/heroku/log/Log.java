
package org.heroku.log;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;


public class Log {
    
    private static Connection getConnection() throws URISyntaxException, SQLException {
        
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
  
        return DriverManager.getConnection(dbUrl, username, password);
}
    
    
    
    public static void logToDb(String course1, String course2, String response) throws SQLException, URISyntaxException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");  
        Connection con = getConnection();
        
        DateTime date = new DateTime();
        
        String insert = "insert into user_input values(?,?,?,'" + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").print(date) + "');";
        
        PreparedStatement pstmt = con.prepareStatement(insert);
        
        pstmt.setString(1, course1);
        pstmt.setString(2, course2);
        pstmt.setString(3, response);
        
        pstmt.executeUpdate();
        
        
      
        
    }
    
    
    
}

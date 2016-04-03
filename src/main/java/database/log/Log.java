
package database.log;

import static database.Connect.getConnection;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;


public class Log {
    
    
    
    
    
    public static void logToDb(String course1, String course2, String response) throws SQLException, URISyntaxException, ClassNotFoundException, IOException{
        
        
        Connection con = getConnection();
        
        DateTime date = (new DateTime()).withZone(DateTimeZone.forID("America/Toronto"));
        
        String insert = "insert into user_input values(?,?,?,'" + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").print(date) + "');";
        
        PreparedStatement pstmt = con.prepareStatement(insert);
        
        pstmt.setString(1, course1);
        pstmt.setString(2, course2);
        pstmt.setString(3, response);
        
        pstmt.executeUpdate();
        
        
    }
    
    public static void logFindPrereqsTo(String course, String response) throws ClassNotFoundException, URISyntaxException, SQLException, IOException{
        
        
        Connection con = getConnection();
        
        DateTime date = (new DateTime()).withZone(DateTimeZone.forID("America/Toronto"));
        
        String insert = "insert into input_find_prereqs_to values(?,?,'" + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").print(date) + "');";
        
        PreparedStatement pstmt = con.prepareStatement(insert);
        
        pstmt.setString(1, course);
        pstmt.setString(2, response);
        
        pstmt.executeUpdate();
        
        con.close();
        
    }
    
    
    public static void logToFile(String text) throws IOException{
        
        
        FileWriter fw = new FileWriter("log.txt",true); //the true will append the new data
        fw.write(text + System.getProperty("line.separator"));//appends the string to the file
        fw.close();
        
        
    }
    
    
    
}

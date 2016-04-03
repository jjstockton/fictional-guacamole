
package database;

import config.GetProperties;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    
    
    
    public static Connection getConnection() {
       Connection con = null;
        URI dbUri = null;
        
        String rawUrl = GetProperties.getConfigVar("DATABASE_URL");

        try {
            dbUri = new URI(rawUrl);
        } catch (URISyntaxException e) {
            System.err.println(e);
        }
        

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

        try {
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            System.err.println(e);
         }
        
        try {
            con = DriverManager.getConnection(dbUrl,username,password);
        }catch(SQLException e){
            System.err.println(e);
        }
        
        return con;
        
    }
    
}

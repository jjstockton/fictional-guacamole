
package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {
    
    
    public static String getConfigVar(String var) throws FileNotFoundException, IOException{
        
        String value = null;
        if((value = System.getenv(var)) == null){
            
            Properties prop = new Properties();

            String s = System.getProperty("file.separator");
            
            InputStream input = new FileInputStream(".." + s + ".." + s + "config.properties");

                // load a properties file
            prop.load(input);

                // get the property value
            value = prop.getProperty(var);
            
                
            input.close();

        }
        
        
	
        return value;
    }
    
    
    
}


package courses;

import database.log.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transcript {
    
    
    public static ArrayList<String> parseTranscript(String transcript) throws IOException{
        
        ArrayList<String> courses = new ArrayList<>();
        
        
        String lines[] = transcript.split("\\r?\\n");
        
        Pattern course = Pattern.compile("(?<subject>\\p{Upper}{2,5})\\s*(?<number>\\d{2,}+\\w{0,1})");  
        Pattern credit = Pattern.compile("\\s+(?<credit>\\p{Upper})\\s+\\p{Upper}\\s+");  
        for(String line : lines){
            
            if(line.contains("Program: ")){
                //System.out.println(line);
            }
            
            Matcher m1 = course.matcher(line);
            
            
            if(m1.find()){
                
                Matcher m2 = credit.matcher(line);
                
                if(m2.find()){
                    //System.out.println(m1.group("subject") + " " + m1.group("number"));
                    if(m2.group("credit").equals("Y")){
                        String crs = m1.group("subject") + " " + m1.group("number");
                        
                        Log.logToFile(crs);
                        
                        courses.add(crs);
                    }
                    
                }
                
                
                //System.out.println(line);
                //System.out.println(m1.group("subject") + " " + m1.group("number"));
            }
            
        }
        
        return courses;
    }
       
    
}

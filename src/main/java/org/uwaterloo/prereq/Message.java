
package org.uwaterloo.prereq;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    
    
   public static String isPrereqMsg(String prereq, String course, String key) throws IOException, JSONException {
       
       //verify that courses are valid
       Pattern pattern = Pattern.compile("(?<subject>\\w{2,}+)\\s*(?<number>\\d{2,}+)");   
       Matcher m1 = pattern.matcher(prereq);
       Matcher m2 = pattern.matcher(course);
       
       
       
       if(!m1.matches() || !m2.matches()){
           return "You didn't enter valid courses :(";
       }
       
       String text = ""; 
        
        prereq = prereq.toUpperCase().replace(" ", ""); 
        course = course.toUpperCase();
        
        URL url = new URL("https://api.uwaterloo.ca/v2/courses/" + m2.group("subject") +  "/" + m2.group("number") + "/prerequisites.json?key=" + key);

        String str;
        try (Scanner scan = new Scanner(url.openStream())) {
            str = new String();
            while (scan.hasNext())
                str += scan.nextLine();
        }

        // build a JSON object
        JSONObject obj = new JSONObject(str);
        
        String prereqsParsed = "";
        
        
        try {
            prereqsParsed = obj.getJSONObject("data").get("prerequisites_parsed").toString();
       
        } catch (JSONException e){
            return prereq + " is not a prerequisite for " + course + "." + "<br>";
        }
        
        
        if(prereqsParsed.isEmpty()){
            return      prereq + " is not a prerequisite for " + course + "." + "<br>";
            
        }
        
        PrereqTree tree = new PrereqTree(prereqsParsed);
        
        if(isRequired(prereq,prereqsParsed)){
            text += (prereq + " is required for " + course + ".") + "<br>";
        }else if (prereqsParsed.contains(prereq)){
            
            ArrayList<PrereqTree> replaceWith = getReplaceWith(tree,prereq);
            ArrayList<PrereqTree> requiredWith = getRequiredWith(tree,prereq);
            
            ArrayList<PrereqTree> otherRequiredCourses = new ArrayList<>();
            
            for(PrereqTree element : replaceWith){
                for(PrereqTree t : getRequiredWith(tree,element.rawPrereqs) ){
                    
                    if(requiredWith.contains(t) && !otherRequiredCourses.contains(t)) 
                        otherRequiredCourses.add(t);
                    
                }
            }
            
            for(int i = 0; i < requiredWith.size(); i++){
              
                if(otherRequiredCourses.contains(requiredWith.get(i))) {
                    requiredWith.remove(i);
                    i--;
                }
                
            }
            
            text += (prereq + " can be used" );
           
            if(!requiredWith.isEmpty()){
                text += (" with ");
                
                String requiredWithRaw = join(requiredWith,requiredWith.size());
                
                
                text += (clean(parsedToText(requiredWithRaw)) + "");
                
            }
              
            text += (" for " + course + ".") + "<br>";
            String replaceWithRaw = join(replaceWith,1);
            
            text += ("Don't want to take " + prereq + "? Take ");
           
          
            text += (clean(parsedToText(replaceWithRaw)) + " instead.") + "<br>";
            
          
           
           if(!otherRequiredCourses.isEmpty()){
               text += ("Other requirements: ");
               String otherRequiredCoursesRaw = join(otherRequiredCourses,otherRequiredCourses.size());
               
                text += (clean(parsedToText(otherRequiredCoursesRaw)) + ".") + "<br>";
           }
        } else {
            
            text += (prereq + " is not a prerequisite for " + course + ".") + "<br>";
        }
        
        return text;
        
    }
    
    public static String join(ArrayList<PrereqTree> trees, int numRequired){
        
        String rawPrereqs = "[" + numRequired + ",";
        
        for(int i = 0; i < trees.size(); i++){
            
            if(i != trees.size() - 1)
                rawPrereqs += trees.get(i).rawPrereqs + ",";
            else
                rawPrereqs += trees.get(i).rawPrereqs;
        }
        
        rawPrereqs += "]";
       
        return rawPrereqs;
        
    }
    
    public static String parsedToText(String prereqsParsed){
        
        
        if(prereqsParsed.isEmpty())
            return "";
        PrereqTree tree = new PrereqTree(prereqsParsed);
        
        if(!prereqsParsed.contains(","))
            return prereqsParsed.replace("\"", "").replace("[","").replace("]","");
        
        
       
        String conjunction;
        if(tree.numRequired == tree.courses.length)
            conjunction = " and ";
        else if (tree.numRequired == 1)
            conjunction = " or ";
        else
            conjunction = tree.numRequired + " of ";
        
        String text = "";

        text = "(";
        
        for(int i = 0; i < tree.courses.length; i++){

            if(i != tree.courses.length - 1)
                text += parsedToText(tree.courses[i]) + conjunction;
            else
                text += parsedToText(tree.courses[i]);
        }

        text += ")";
     
        
        
        return text;
        
    }
    
    
    
    public static String clean(String text){
        
        if((text.charAt(0) + "").equals("(") && (text.charAt(text.length() - 1) + "").equals(")")){
            text = text.substring(1,text.length() - 1);
        }
        
        if(!text.contains("and"))
            text = text.replace("(","").replace(")", "");
        
        
        return text;
        
    }
    
    public static ArrayList<PrereqTree> getReplaceWith(PrereqTree tree, String prereq){
        
        if(!prereq.contains("\""))
            prereq = "\"" + prereq + "\"";
        
        
     
        ArrayList<PrereqTree> replaceWith = new ArrayList<>();
        ArrayList<String> locations = PrereqTree.getLocations(tree,prereq);
        ArrayList<PrereqTree> trees = getTrees(tree,locations);
        
        for(int i = 0; i < trees.size(); i++){
        
            String location = locations.get(i);
            PrereqTree temp;
            while(true){

                temp = getTree(tree,location);
                if(temp.numRequired != temp.courses.length){
                    for(int n = 0; n < temp.courses.length; n++){

                        if(!temp.courses[n].contains(prereq))  
                            replaceWith.add(temp.prereqTree.get(n));

                    }
                }

                if(location.length() == 0) 
                    break;

                location = location.substring(0, location.length() - 1);


            }
        }
        
        return replaceWith;
    }
    
    
    public static ArrayList<PrereqTree> getRequiredWith(PrereqTree tree, String prereq){
        
        if(!prereq.contains("\""))
            prereq = "\"" + prereq + "\"";
        
        ArrayList<String> locations = PrereqTree.getLocations(tree,prereq);
        ArrayList<PrereqTree> trees = getTrees(tree,locations);
        ArrayList<PrereqTree> requiredWith = new ArrayList<>();
        
        for(int i = 0; i < trees.size(); i++){
            
            String location = locations.get(i);
            PrereqTree temp;
            while(true){

                temp = getTree(tree,location);

                if(temp.numRequired == temp.courses.length){
                    for(int n = 0; n < temp.courses.length; n++){

                        if(!temp.courses[n].contains(prereq))  
                            requiredWith.add(temp.prereqTree.get(n));

                    }
                }

                if(location.length() == 0) 
                    break;

                location = location.substring(0, location.length() - 1);

            }
        }
        
        return requiredWith;
 
    }
    

    public static boolean isRequired(String prereq, String prereqsParsed) throws MalformedURLException, IOException{
        
        prereq = prereq.toUpperCase().replace(" ",""); 
        
        PrereqTree tree = new PrereqTree(prereqsParsed);
    
        if(!prereqsParsed.contains(prereq)) 
            return false;
        

        ArrayList<String> locations = PrereqTree.getLocations(tree, prereq);
        for(String location : locations){
            boolean required = true;


            while(required && location.length() > 0){


                location = location.substring(0,location.length() - 1);

                PrereqTree temp = getTree(tree,location);

                if(temp.numRequired != temp.courses.length)     
                    return false;

            }
        }


        return true;
    }
    
    
    public static boolean isRequired(String prereq, PrereqTree tree) throws MalformedURLException, IOException{
        
        String prereqsParsed = tree.rawPrereqs;
        
        prereq = prereq.toUpperCase().replace(" ",""); 
        
        
    
        
        if(!prereqsParsed.contains(prereq))
            return false;
     
        ArrayList<String> locations = PrereqTree.getLocations(tree, prereq);
        
        for(String location : locations){
            boolean required = true;


            while(required && location.length() > 0){


                location = location.substring(0,location.length() - 1);

                PrereqTree temp = getTree(tree,location);

                if(temp.numRequired != temp.courses.length)     return false;

            }
        }

        return true;
    }


    
    public static ArrayList<PrereqTree> getTrees(PrereqTree tree, ArrayList<String> locations){
        
        ArrayList<PrereqTree> trees = new ArrayList<>();
        
        for(String location : locations){
            while(location.length() > 0){

                tree = tree.prereqTree.get(Integer.parseInt(location.charAt(0) + ""));
                location = location.substring(1);
                
            }
            trees.add(tree);
        }
        return trees;
        
    }
    
    
    
    public static PrereqTree getTree(PrereqTree tree, String location){
      
        
        while(location.length() > 0){

            tree = tree.prereqTree.get(Integer.parseInt(location.charAt(0) + ""));
            location = location.substring(1);

        }
        
        
        return tree;
        
    }
    
    public static void findprereqsto(String course, String key) throws FileNotFoundException, IOException{    
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\Documents\\courses.txt"));
        
        String line = null;
        ArrayList<String> courses = new ArrayList<>();
        while((line = br.readLine()) != null){
            courses.add(line);
        }
        
        
        
        
        int count = 0;
        for(String crs : courses){
            String subject = crs.substring(0,crs.indexOf(" "));
            String code = crs.substring(crs.indexOf(" ") + 1);
            
            URL url = new URL("https://api.uwaterloo.ca/v2/courses/" + subject + "/" + code + "/prerequisites.json?key=" + key);

            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext())
                str += scan.nextLine();
            scan.close();

            // build a JSON object
            JSONObject obj = new JSONObject(str);
            
            
            if (obj.getJSONObject("meta").getInt("status") == 200){
                
                String prereqs = obj.getJSONObject("data").get("prerequisites_parsed").toString();
               
                if(prereqs.contains(course)){
                    
                System.out.println("-------");
                isPrereqMsg(course,crs,key);
                System.out.println("-------");
                    
                }
            }
           
            
            if(count % 100 == 0)
                System.out.println(count);

            count++;
        }
        
    
    }
        
    public static void getCourses(String key) throws MalformedURLException, IOException {    
        // build a URL
    String s = "https://api.uwaterloo.ca/v2/codes/subjects.json?key=" + key;
 
    URL url = new URL(s);
 
    // read from the URL
    Scanner scan = new Scanner(url.openStream());
    String str = new String();
    while (scan.hasNext())
        str += scan.nextLine();
    scan.close();
 
    // build a JSON object
    JSONObject obj = new JSONObject(str);
    
    if (obj.getJSONObject("meta").getInt("status") != 200)
        return;
    
    
    JSONArray data = obj.getJSONArray("data");
    
    ArrayList<String> subjects = new ArrayList<>();
    for(int i = 0; i < data.length(); i++){
        
        subjects.add(data.getJSONObject(i).getString("subject"));
    }
    
    
    ArrayList<String> courses = new ArrayList<>();
    for(String subject : subjects){
        
        
        URL courseURL = new URL("https://api.uwaterloo.ca/v2/courses/" + subject + ".json?key=" + key);
 
        // read from the URL
        Scanner courseScan = new Scanner(courseURL.openStream());
        str = new String();
        while (courseScan.hasNext())
            str += courseScan.nextLine();
        scan.close();

        // build a JSON object
        obj = new JSONObject(str);
        
        data = obj.getJSONArray("data");
        
        for(int i = 0; i < data.length(); i++){
            String title = data.getJSONObject(i).getString("title");
            String code = data.getJSONObject(i).getString("catalog_number");
            String level = data.getJSONObject(i).getString("academic_level");
            
            if(level.equals("undergraduate")){
                
            
                System.out.println(subject + " " + code);

                courses.add(subject + " " + code);


                FileWriter fw = new FileWriter("C:\\Users\\user\\Documents\\courses.txt",true); 
                fw.write(subject + " " + code + System.getProperty("line.separator"));

                fw.close();
            }
        }
       
    }
   
    System.out.println(courses.size());
    
    }
        
    public static void courseOffering() throws MalformedURLException, IOException {
        String course = "ECE 105";
        
        
        URL url = new URL("https://api.uwaterloo.ca/v2/terms/1139/CS/115/schedule.json");
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        
        String line = null;
        
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
        
    }
    
}

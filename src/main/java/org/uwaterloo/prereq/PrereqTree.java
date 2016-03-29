
package org.uwaterloo.prereq;

import java.util.ArrayList;
import java.util.Arrays;

public class PrereqTree {
    
    
    int numRequired;
    ArrayList<PrereqTree> prereqTree = new ArrayList<>();
    String[] courses;
    
    String rawPrereqs;
   
    public PrereqTree(String prereqs){
        
       
        
        rawPrereqs = prereqs;
        
        if((prereqs.charAt(0) + "").equals("["))   prereqs = prereqs.substring(1,prereqs.length() - 1);
     
        String tmp_prereqs = prereqs;
        
        String split = "jacob rocks"; //I can put anything I want here :)
        for(int i = 0; i < tmp_prereqs.length(); i++){
            
            if((tmp_prereqs.charAt(i) + "").equals(",") && bracketCount(tmp_prereqs,i) == 0)
                tmp_prereqs = tmp_prereqs.substring(0,i) + split +tmp_prereqs.substring(i + 1);
                
        }
        
       
        String[] elements = tmp_prereqs.split(split);
       
        for(int i = 0; i < elements.length; i++){
            while(bracketCount(elements[i],elements[i].length() - 1) > 0){
                elements[i] += "]";
            }
            
            while(bracketCount(elements[i],elements[i].length() - 1) < 0){
                elements[i] = "[" + elements[i];
            }
        }
        
        try {
            
            numRequired = Integer.parseInt(elements[0].replace("[", "").replace("]",""));
            elements = Arrays.copyOfRange(elements,1,elements.length);
            
        } catch(NumberFormatException e){
            numRequired = elements.length;
        }
        
        courses = elements;
        
        for(String element : elements){
            if(!element.equals(prereqs)){
                
                prereqTree.add(new PrereqTree(element));
            }
        }
       
    }
    
    
    
    
     public PrereqTree(ArrayList<String> crs, int num){
 
        crs.toArray(courses);
        
        for(String element : crs){

            if(element.contains(","))
                prereqTree.add(new PrereqTree(element));
            
        }
       
        
    }
    

     
     
     
     public static ArrayList<String> getLocations(PrereqTree tree, String course){
  
        
         return getLocations(tree,course,new ArrayList<String>(),"");
     }
     
     
     public static ArrayList<String> getLocations(PrereqTree tree, String course, ArrayList<String> locations, String location){
         
    
         for(int i = 0; i < tree.courses.length; i++){
  
            if(tree.courses[i].replace("\"","").equals( course.replace("\"","") )){
                locations.add(location + i);
                return locations;
            }
          
            if(tree.courses[i].contains(course))    
                return getLocations(tree.prereqTree.get(i),course,locations, location + i);
             
        }
        
     
         return locations;
         
    }
     
     
    
    public static int bracketCount(String str, int index) {
            
        int count = 0;
        for(int i = 0; i <= index; i++){
            
            if((str.charAt(i) + "").equals("[")){
                count++;
            }else if ((str.charAt(i) + "").equals("]")){
                count--;
            }
            
            
            
        }
    
        return count;
    
    }

    
        
    
}


    


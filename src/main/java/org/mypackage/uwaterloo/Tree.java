/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mypackage.uwaterloo;

/**
 *
 * @author user
 */
import java.util.ArrayList;
import java.util.Arrays;

public class Tree {
    
    
    int numRequired;
    ArrayList<Tree> prereqTree = new ArrayList<>();
    String[] courses;
    
    String rawPrereqs;
   
    public Tree(String prereqs){
        
        //System.out.println(prereqs);
        
        rawPrereqs = prereqs;
        
        if((prereqs.charAt(0) + "").equals("["))   prereqs = prereqs.substring(1,prereqs.length() - 1);
      
        //System.out.println(prereqs);
 
        String tmp_prereqs = prereqs;
        
        String split = "jacob rocks"; //I can put anything I want here :)
        for(int i = 0; i < tmp_prereqs.length(); i++){
            
            if((tmp_prereqs.charAt(i) + "").equals(",") && bracketCount(tmp_prereqs,i) == 0){
                
                tmp_prereqs = tmp_prereqs.substring(0,i) + split +tmp_prereqs.substring(i + 1);
                
                
            }
            
        }
        
       
        String[] elements = tmp_prereqs.split(split);
        
        //for(String element : elements) System.out.println(element);
        
        //System.out.println("---");
       
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
            //System.out.println(element);
            if(!element.equals(prereqs)){
                
                //System.out.println(element);
                prereqTree.add(new Tree(element));
            }
        }
       
    }
    
    
    
    
     public Tree(ArrayList<String> crs, int num){
 
        
        crs.toArray(courses);
        
        for(String element : crs){

            if(element.contains(",")){
                
                //System.out.println(element);
                prereqTree.add(new Tree(element));
            }
        }
       
        
    }
    
    
    
     
     
     
//     public static String getLocation(Tree tree, String course){
//  
//        // String location = getLocation(tree,course,"");
//        // System.out.println(location);
//         return getLocation(tree,course,"");
//     }
//     
//     
//     public static String getLocation(Tree tree, String course, String location){
//         
//         
//        //System.out.println(location);
//         for(int i = 0; i < tree.courses.length; i++){
//            //System.out.println(tree.courses[i] + " vs " + course);
//          //  System.out.println(location + i);
//            if(tree.courses[i].replace("\"","").equals( course.replace("\"","") )){
//                //System.out.println("returning " + (location + i));
//                return location + i;
//            }
//          
//            if(tree.courses[i].contains(course))    return getLocation(tree.prereqTree.get(i),course,location + i);
//             
//        }
//        
//         //System.out.println("here");
//         return location;
//         
//    }
     
     
     
     public static ArrayList<String> getLocations(Tree tree, String course){
  
        // String location = getLocation(tree,course,"");
        // System.out.println(location);
         return getLocations(tree,course,new ArrayList<String>(),"");
     }
     
     
     public static ArrayList<String> getLocations(Tree tree, String course, ArrayList<String> locations, String location){
         
         
        //System.out.println(location);
         for(int i = 0; i < tree.courses.length; i++){
            //System.out.println(tree.courses[i] + " vs " + course);
          //  System.out.println(location + i);
            if(tree.courses[i].replace("\"","").equals( course.replace("\"","") )){
                locations.add(location + i);
                return locations;
            }
          
            if(tree.courses[i].contains(course))    return getLocations(tree.prereqTree.get(i),course,locations, location + i);
             
        }
        
         //System.out.println("here");
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


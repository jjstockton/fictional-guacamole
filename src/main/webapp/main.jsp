<%-- 
    Created on : 27-Mar-2016, 7:15:52 PM
    Author     : Jacob
--%>


<%@page import="org.uwaterloo.prereq.Message"%>
<%@page import="org.heroku.log.Log"%>
<%@page import="config.GetProperties"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Courses</title>
    </head>
    <body>
        
        <% 
            String key = GetProperties.getConfigVar("API_KEY");
            String prereq = request.getParameter("prereq"); 
            String course = request.getParameter("course");
            String text;
            try {
                text = Message.isPrereqMsg(prereq, course, key);
            }catch(Exception e){
                text = e.toString();
            }
                    
                
            Log.logToDb(prereq,course,text);
        %>
    
        <%= text %>
    </body>
</html>

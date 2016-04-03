<%-- 
    Created on : 31-Mar-2016, 10:28:03 PM
    Author     : Jacob
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.uwaterloo.prereq.Message"%>
<%@page import="database.log.Log"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="courses.js"></script>
        <link rel="stylesheet" type="text/css" href="courses.css">
        <title>Courses</title>
    </head>
    <body>
             
        <% 
            String course = request.getParameter("course");
            String text;
            try {
                text = Message.getPrereqsTo(course);
            }catch(Exception e){
                text = e.toString();
            }

            Log.logFindPrereqsTo(course,text);
        %>
    
        <div id="courses"><%= text %></div>
        
        <script>
            
            var text = document.getElementById("courses").innerHTML;
            var output = formatCourses(text);
            document.getElementById("courses").innerHTML = output;
            
        </script>
        
        
    </body>
</html>

<%-- 
    Document   : main
    Created on : 27-Mar-2016, 7:15:52 PM
    Author     : user
--%>

<%@page import="org.mypackage.uwaterloo.Uwaterloo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="jacobispoo.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <% 
            String key = System.getenv("API_KEY");
            String prereq = request.getParameter("prereq"); 
            String course = request.getParameter("course");
            String text = Uwaterloo.isPrereqMsg(prereq, course,key);
        %>
    
        <%= text %>
    </body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="database.User"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Register</title>
    </head>
    <body>
        <%
    String user = request.getParameter("usernamesignup");    
    String pwd = request.getParameter("passwordsignup");
   
    
    if(User.createUser(user,pwd)){
        session.setAttribute("userid", user);
    
        response.sendRedirect("index.html");
    }else{
        
        response.sendRedirect("signup.jsp");
        
    }

        %>
        
    </body>
</html>

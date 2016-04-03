
<%@page import="database.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        String user = request.getParameter("username");    
        String pwd = request.getParameter("password");

        
        if(User.authenticateUser(user,pwd)){
            session.setAttribute("userid", user);

            response.sendRedirect("mycourses/courses.jsp");
            
        }else {
            response.sendRedirect("signup.jsp");
        }
                

       
    
    %>
    </body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>My Courses</title>
    </head>
    <body>
        
        <h1>My Courses</h1>
        <%
    
            String username = session.getAttribute("userid").toString();
            
            
        %>
        
        It doesn't look like you have any courses. <a href="add.jsp">Add courses</a>. 
        
    </body>
</html>

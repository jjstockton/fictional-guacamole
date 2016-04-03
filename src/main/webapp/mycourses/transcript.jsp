<%@page import="java.util.ArrayList"%>
<%@page import="courses.Transcript"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%   ArrayList<String> courses = Transcript.parseTranscript(request.getParameter("transcript"));
            String output = ""; 
            for(String course : courses){
                output += course + "<br>";
            }
            //output = courses;
        %>
        
        <%=courses%>
        
    </body>
</html>

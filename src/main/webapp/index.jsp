<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Courses</title>
    </head>
    <body>
        <form action="main.jsp" method="GET">
            Course 1: <input type="text" name="prereq">
            <br />
            Course 2: <input type="text" name="course" />
            <input type="submit" value="Submit" />
             <br />
        </form>
       
            <br />
            <br />
         Or enter a course below to see all courses for which it is a prerequisite:
         <br/>
         <form action="getPrereqsTo.jsp" method="GET">
            Course : <input type="text" name="course">
            <input type="submit" value="Submit" />
             <br />
        </form>
        

    </body>
</html>

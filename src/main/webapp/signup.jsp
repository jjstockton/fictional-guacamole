

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<head>

    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 

</head>
<body>

    <form  action="login.jsp" method="POST"> 
        <h1>Log in</h1> 
        <p> 
            <label for="username" class="uname" data-icon="u" > Your username </label>
            <input id="username" name="username" required="required" type="text" placeholder="Username"/>
        </p>
        <p> 
            <label for="password" class="youpasswd" data-icon="p"> Your password </label>
            <input id="password" name="password" required="required" type="password" placeholder="Password" /> 
        </p>

        <p class="login button"> 
            <input type="submit" value="Login" /> 
        </p>                               
    </form>

    <form  action="registration.jsp" method="POST"> 
        <h1> Sign up </h1> 
        <p> 
            <label for="usernamesignup" class="uname" data-icon="u">Your username</label>
            <input id="usernamesignup" name="usernamesignup" required="required" type="text"  placeholder="Username"/>
        </p>

        <p> 
            <label for="passwordsignup" class="youpasswd" data-icon="p">Your password </label>
            <input id="passwordsignup" name="passwordsignup" required="required" type="password" placeholder="Password"/>
        </p>
        <p> 
            <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your password </label>
            <input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required" type="password" placeholder="Password"/>
        </p>
        <p class="signin button"> 
            <input type="submit" value="Sign up"/> 
        </p>
    </form>




</body>


</html>

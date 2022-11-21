<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.jsu.mcis.project2.dao.*"%>

<jsp:useBean id="projBean" class="edu.jsu.mcis.project2.Bean" scope="session"/>
<jsp:setProperty name="projBean" property="*" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <script type="text/javascript" src="scripts/p2JS.js"></script>
        <script type="text/javascript" src="scripts/jquery-3.6.1.min.js"></script>
        <link rel="stylesheet" type="text/css" media="screen" href="style.css">
    </head>
    <body>
        <a href="landing.jsp">Back</a>
        <h1>Register for a Course:</h1>
        <form name="regform" id="regform">
            <fieldset>
                <legend>Enter a CRN:</legend>
                <p>
                    CRN:
                    <input id="crn" type="number" name="crn">
                </p>
                <input type="submit" value="Submit" onclick ="return Project2.register();">
            </fieldset>
        </form>
            <div id="output" name="output"></div>
        <p>
            <input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/main/logout.jsp', '_self', false);" />
        </p>
        
    </body>
    <script>
        Project2.getSchedule();
    </script>
  
</html>

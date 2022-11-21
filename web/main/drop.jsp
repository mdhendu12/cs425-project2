<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Drop Course</title>
        <script type="text/javascript" src="scripts/p2JS.js"></script>
        <script type="text/javascript" src="scripts/jquery-3.6.1.min.js"></script>
        <link rel="stylesheet" type="text/css" media="screen" href="style.css">
    </head>
    <body>
        <a href="landing.jsp">Back</a>
        <h1>Drop a Course:</h1>
        <form name="delform" id="delform">
            <fieldset>
                <legend>Enter a CRN:</legend>
                <p>
                    CRN:
                    <input id="crn" type="number" name="crn">
                </p>
                <input type="submit" value="Submit" onclick ="return Project2.delete();">
            </fieldset>
        </form>
        <div id="output" name="output"></div>
        <p>
            <input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/main/logout.jsp', '_self', false);" />
        </p>
        
        <script>
            Project2.getSchedule();
        </script>
        
    </body>
  
</html>

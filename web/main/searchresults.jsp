<%@page import="java.util.Locale"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.jsu.mcis.project2.dao.*"%>

<jsp:useBean id="projBean" class="edu.jsu.mcis.project2.Bean" scope="session"/>
<jsp:setProperty name="projBean" property="*" />

<%
    DAOFactory daoFactory = null;
    ServletContext context = request.getServletContext();

    if (context.getAttribute("daoFactory") == null) {
        System.err.println("*** Creating new DAOFactory ...");
        daoFactory = new DAOFactory();
        context.setAttribute("daoFactory", daoFactory);
    }
    else {
        daoFactory = (DAOFactory) context.getAttribute("daoFactory");
    }
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Results</title>
        <script type="text/javascript" src="scripts/p2JS.js"></script>
        <script type="text/javascript" src="scripts/jquery-3.6.1.min.js"></script>
        <link rel="stylesheet" type="text/css" media="screen" href="style.css">
    </head>
    <body>
        <a href="landing.jsp">Back</a>
        <h1>Sections Found:</h1>
        <div id="output" name="output"></div>
        
        <script type="text/javascript">
            Project2.getSearchResults();
        </script>
        <p>
            <input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/main/logout.jsp', '_self', false);" />
        </p>
    </body>
    
    
</html>

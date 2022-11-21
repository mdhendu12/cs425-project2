<%@page import="edu.jsu.mcis.project2.dao.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="daoFactory" class="edu.jsu.mcis.project2.dao.DAOFactory" scope="application"/>
<%
    SearchDAO dao = daoFactory.getSearchDAO();
%>
<!DOCTYPE html>
<html>
    
    <head>
        
        <title>Schedule Report</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="scripts/jquery-3.6.1.min.js"></script>
        <script src="scripts/p2JS.js"></script>
        <link rel="stylesheet" href="style.css" />
        
    </head>
    
    <body>
        
        <p>
            <a href="<%= request.getContextPath() %>/main/landing.jsp">Back to Home</a>
        </p>
        
        <form id="reportform" name="reportform" method="GET" action="report" target="_blank">
            
            <fieldset>
                
                <legend>View Schedule Report</legend>
        
                <p>
                    <label for="termid"><strong>View Schedule Report by Term:</strong><br /></label>
                    <%= dao.getTermListAsHTML() %>
                </p>

                <input type="submit" value="Submit">
                <input type="reset" value="Reset">
                
            </fieldset>
        
        </form>
        
    </body>
    
</html>
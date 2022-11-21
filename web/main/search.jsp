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
    
    SearchDAO searchDAO = daoFactory.getSearchDAO();
        
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <a href="landing.jsp">Back</a>
        <form action="<%= request.getContextPath() %>/main/searchresults.jsp" method="GET">
            <fieldset>
                <legend>Class Schedule Search</legend>
                <p>
                    Subject:
                    <%= searchDAO.getSubjectListAsHTML() %>
                <p>

                <p>
                    Course Number:
                    <input id="courseNumber" type="number" name="courseNumber">
                </p>
                    
                <p>
                    
                <p>
                    Course Level:
                    <%= searchDAO.getLevelListAsHTML() %>
                <p>
                    
                <p>
                    Schedule Type:
                    <%= searchDAO.getScheduleTypeListAsHTML() %>
                <p>
                    
                <p>
                    Start Time:
                    <%= searchDAO.getTimeFieldsAsHTML("start") %>
                </p>
                <p>
                    End Time:
                    <%= searchDAO.getTimeFieldsAsHTML("end") %>
                </p>
                
                <p>
                    Days:
                    <input type="checkbox" name="daymon" value="M" ID="daymon" />
                    <abbr title = Monday>Mon</abbr>
                    
                    <input type="checkbox" name="daytue" value="T" ID="daytue" />
                    <abbr title = Tuesday>Tue</abbr>
                    
                    <input type="checkbox" name="daywed" value="W" ID="daywed" />
                    <abbr title = Wednesday>Wed</abbr>
                    
                    <input type="checkbox" name="daythur" value="R" ID="daythur" />
                    <abbr title = Thursday>Thur</abbr>
                    
                    <input type="checkbox" name="dayfri" value="F" ID="dayfri" />
                    <abbr title = Friday>Fri</abbr>
                    
                </p>
                
                <input type="submit" value="Submit">
                
            </fieldset>
        </form>
        <p>
            <input type="button" value="Logout" onclick="window.open('<%= request.getContextPath() %>/main/logout.jsp', '_self', false);" />
        </p>
    </body>
</html>

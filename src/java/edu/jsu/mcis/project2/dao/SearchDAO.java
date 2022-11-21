package edu.jsu.mcis.project2.dao;

import edu.jsu.mcis.project2.Bean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SearchDAO {

    private final DAOFactory daoFactory;
    
    private final String QUERY_SUBJECT_LIST = "SELECT * FROM subject ORDER BY id";
    private final String QUERY_LEVEL_LIST = "SELECT * FROM level ORDER BY id";
    private final String QUERY_SCHEDULETYPE_LIST = "SELECT * FROM scheduletype ORDER BY id";
    private final String QUERY_TERM_LIST = "SELECT * FROM term ORDER BY id";
    private static final String QUERY_FIND = "SELECT course.*, section.*, "
    + "term.name AS termname, term.`start` AS termstart, term.`end` AS termend, "
    + "scheduletype.description as scheduletype, `level`.description as `level` "
    + "FROM ((((section JOIN scheduletype ON section.scheduletypeid = scheduletype.id) "
    + "JOIN course ON section.subjectid = course.subjectid AND section.num = course.num) "
    + "JOIN `level` ON course.levelid = `level`.id) "
    + "JOIN term ON section.termid = term.id) "
    + "WHERE ((? IS NULL OR course.subjectid = ?) "    // subjectid (parameters 1 & 2)
    + "AND (? IS NULL OR course.num = ?) "             // num (parameters 3 & 4)
    + "AND (? IS NULL OR `level`.id = ?) "             // levelid (parameters 5 & 6)
    + "AND (? IS NULL OR section.scheduletypeid = ?) " // scheduletypeid (parameters 7 & 8)
    + "AND (? IS NULL OR section.`start` >= ?) "       // start as LocalTime (parameters 9 & 10)
    + "AND (? IS NULL OR section.`end` <= ?) "         // end as LocalTime (parameters 11 & 12)
    + "AND (? IS NULL OR section.days REGEXP ?) "      // days (ex: "M|W|F") (parameters 13 & 14)
    + "AND (section.termid = ?)) "                     // termid (parameter 15)
    + "ORDER BY course.num, section";

    SearchDAO(DAOFactory dao) {
        this.daoFactory = dao;
    }
    
    public String getSubjectListAsHTML() {
        
        StringBuilder s = new StringBuilder();

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_SUBJECT_LIST);
            
            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();
                
                s.append("<select name=\"subject\" id=\"subject\" size=\"10\">");
                
                while (rs.next()) {
                    
                    String id = rs.getString("id");
                    String description = rs.getString("name");
                    
                    s.append("<option value=\"").append(id).append("\">");
                    s.append(description);
                    s.append("</option>");
                                        
                }
                
                s.append("</select>");

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }

        }
        
        return s.toString();
        
    }
    
    public String getScheduleTypeListAsHTML() {
        
        StringBuilder s = new StringBuilder();

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_SCHEDULETYPE_LIST);
            
            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();
                
                s.append("<select name=\"scheduleType\" id=\"scheduleType\"size=\"3\">");
                s.append("<option value=\"0\" selected=\"\">All</option>");
                
                while (rs.next()) {
                    
                    String id = rs.getString("id");
                    String description = rs.getString("description");
                    
                    s.append("<option value=\"").append(id).append("\">");
                    s.append(description);
                    s.append("</option>");
                                        
                }
                
                s.append("</select>");

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }

        }
        
        return s.toString();
        
    }
    
    public String getLevelListAsHTML() {
        
        StringBuilder s = new StringBuilder();

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_LEVEL_LIST);
            
            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();
                
                s.append("<select name=\"courseLevel\" id=\"courseLevel\"size=\"3\">");
                
                s.append("<option value=\"0\" selected=\"\">All</option>");
                
                while (rs.next()) {
                    
                    String id = rs.getString("id");
                    String description = rs.getString("description");
                    
                    s.append("<option value=\"").append(id).append("\">");
                    s.append(description);
                    s.append("</option>");
                                        
                }
                
                s.append("</select>");

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }

        }
        
        return s.toString();
        
    }
    
    public String getTermListAsHTML() {
        
        StringBuilder s = new StringBuilder();

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_TERM_LIST);
            
            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();
                
                s.append("<select name=\"term\" id=\"term\">");
                
                while (rs.next()) {
                    
                    String id = rs.getString("id");
                    String description = rs.getString("name");
                    
                    s.append("<option value=\"").append(id).append("\">");
                    s.append(description);
                    s.append("</option>");
                                        
                }
                
                s.append("</select>");

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }

        }
        
        return s.toString();
        
    }
    
    public String getTimeFieldsAsHTML(String type) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Hour <select id=\"").append(type).append("hour\" name=\"").append(type)
                .append("hour\" size=\"1\">");
        for (int i = 0; i <= 12; i++) {
            sb.append("<option value=\"").append(i).append("\">")
                    .append(String.format("%02d", i)).append("</option>");
        }
        sb.append("</select>");
        
        sb.append(" Minute <select id=\"").append(type).append("min\" name=\"").append(type)
                .append("min\" size=\"1\">");
        for (int i = 0; i <= 55; i+=5) {
            sb.append("<option value=\"").append(i).append("\">")
                    .append(String.format("%02d", i)).append("</option>");
        }
        sb.append("</select>");
        
        sb.append(" AM/PM <select id=\"").append(type).append("ap\" name=\"").append(type)
                .append("ap\" size=\"1\"><option value=\"a\">am</option><option value=\"p\">pm</option></select>");
        
        return sb.toString();
    }
    
    public String find(HashMap<String,String> params) {
        JSONObject json = new JSONObject();
        JSONArray sections = new JSONArray();

        json.put("success", false);

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String[] paramArr = {params.get("subject"), params.get("courseNumber"), params.get("courseLevel"),
                params.get("scheduleType"), params.get("start"), params.get("end"), params.get("days"), 
                params.get("term")};
            
            for (String param : paramArr) {
                System.out.println(param);
            }
            ps = conn.prepareStatement(QUERY_FIND);
            System.out.println();
            ps.setString(1, paramArr[0]);
            ps.setString(2, paramArr[0]);
            
            ps.setString(3, paramArr[1]);
            ps.setString(4, paramArr[1]);
            
            ps.setString(5, paramArr[2]);
            ps.setString(6, paramArr[2]);
            
            ps.setString(7, paramArr[3]);
            ps.setString(8, paramArr[3]);
            
            ps.setString(9, paramArr[4]);
            ps.setString(10, paramArr[4]);
            
            ps.setString(11, paramArr[5]);
            ps.setString(12, paramArr[5]);
            
            ps.setString(13, paramArr[6]);
            ps.setString(14, paramArr[6]);
            
            ps.setString(15, paramArr[7]);
            
            boolean hasresults = ps.execute();

            if (hasresults) {
                json.put("success", hasresults);

                rs = ps.getResultSet();
                
                DateTimeFormatter date = DateTimeFormatter.ofPattern("LLL dd, YYYY");
                DateTimeFormatter time = DateTimeFormatter.ofPattern("h:mm a");

                
                while (rs.next()) {
                    JSONObject section = new JSONObject();
                    
                    section.put("subjectid", rs.getString("subjectid"));
                    section.put("num", rs.getString("num"));
                    section.put("levelid", rs.getString("levelid"));
                    section.put("scheduletypeid", rs.getString("scheduletypeid"));
                    section.put("start", time.format(LocalTime.parse(rs.getString("start"))));
                    section.put("end", time.format(LocalTime.parse(rs.getString("end"))));
                    section.put("days", rs.getString("days"));
                    section.put("description", rs.getString("description"));
                    section.put("crn", rs.getString("crn"));
                    section.put("termid", rs.getString("termid"));
                    section.put("where", rs.getString("where"));
                    section.put("termstart", date.format(LocalDate.parse(rs.getString("termstart"))));
                    section.put("termend", date.format(LocalDate.parse(rs.getString("termend"))));
                    section.put("termname", rs.getString("termname"));
                    section.put("instructor", rs.getString("instructor"));
                    section.put("section", rs.getString("section"));
                    section.put("level", rs.getString("level"));
                    section.put("credits", String.format("%.3f", Double.parseDouble(rs.getString("credits"))));
                    section.put("scheduletype", rs.getString("scheduletype"));
                    
                    sections.add(section);
                }
                json.put("sections", sections);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }

        }

        return JSONValue.toJSONString(json);
    }
    
}
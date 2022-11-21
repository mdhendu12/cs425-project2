package edu.jsu.mcis.project2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class RegistrationDAO {
    private final DAOFactory daoFactory;
    
    private static final String QUERY_FIND = "SELECT course.*, section.*, "
    + "term.name AS termname, term.`start` AS termstart, term.`end` AS termend, "
    + "scheduletype.description as scheduletype, `level`.description as `level` "
    + "FROM (((((section JOIN scheduletype ON section.scheduletypeid = scheduletype.id) "
    + "JOIN course ON section.subjectid = course.subjectid AND section.num = course.num) "
    + "JOIN `level` ON course.levelid = `level`.id) "
    + "JOIN registration ON registration.crn = section.crn) "
    + "JOIN term ON term.id = registration.termid) "
    + "WHERE registration.studentid = ? AND registration.termid = ?";
    
    
    private static final String QUERY_CREATE = "INSERT into registration (studentid, termid, crn) VALUES (?, ?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM registration WHERE termid=? AND studentid=? AND crn=?";

    
    RegistrationDAO(DAOFactory dao) {
        this.daoFactory = dao;
    }
    
    public String find(int termid, int studentid) {
        JSONObject json = new JSONObject();
        JSONArray sections = new JSONArray();

        json.put("success", false);

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
           
            ps = conn.prepareStatement(QUERY_FIND);
            ps.setInt(1, termid);
            ps.setInt(2, studentid);
            
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
    
    public String create(int studentid, int termid, int crn) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_CREATE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int updateCount = ps.executeUpdate();
                boolean hasResults = updateCount > 0;
                if (hasResults) {
                    return this.find(termid, studentid);
                }
            }
                                
        }
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return "This is not a valid CRN!";
    }
    
    public Integer getStudentId(String username) {
        Integer id = null;
        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM student WHERE username = ?");
            ps.setString(1, username);
            boolean hasresults = ps.execute();
            if ( hasresults ) {
                rs = ps.getResultSet();
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally {
            if (rs != null) { try {
                    rs.close();
                    rs = null;
                } catch (Exception e) { e.printStackTrace(); }
            }
            if (ps != null) { try {
                    ps.close();
                    ps = null;
                } catch (Exception e) { e.printStackTrace(); }
            }
            if (conn != null) { try {
                    conn.close();
                    conn = null;
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
        return id;
    }
    
    public String delete(int termid, int studentid, int crn) {
        JSONObject json = new JSONObject();
        json.put("success", false);
        
        PreparedStatement ps = null;
        
        try { 
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_DELETE);
                ps.setInt(1, termid);
                ps.setInt(2, studentid);
                ps.setInt(3, crn);
                
                int updateCount = ps.executeUpdate();
                
                if (updateCount > 0) {
                    json.put("success", true);
                }
                
            }
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return JSONValue.toJSONString(json);
    }
}

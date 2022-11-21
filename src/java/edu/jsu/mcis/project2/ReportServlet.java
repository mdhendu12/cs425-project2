package edu.jsu.mcis.project2;

import edu.jsu.mcis.project2.dao.*;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import org.json.simple.*;

public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        // Acquire DAOFactory
        
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

        // Open Report File; Set Response Type
        
        InputStream reportFile = this.getClass().getResourceAsStream("resources" + File.separator + "ScheduleReport.jasper");

        response.setContentType("application/pdf");

        // Open Servlet Output Stream
        
        try ( ServletOutputStream out = response.getOutputStream()) {

            // Acquire DAO
            
            RegistrationDAO dao = daoFactory.getRegistrationDAO();

            // Get Student/Term IDs
            
            int studentid = dao.getStudentId(request.getRemoteUser());
            int termid = Integer.parseInt(request.getParameter("term"));

            // Get Schedule Data; Convert to JasperReports Data Source
            JSONObject json = (JSONObject) JSONValue.parse(dao.find(termid, studentid));
            JSONArray schedule = (JSONArray) json.get("sections");

            JRDataSource jasperDataSource = new JRMapArrayDataSource(schedule.toArray());

            // Initialize Report Parameter Collection (add subtitle with date)
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/dd/yyyy h:mm:ss a");
            String now = dtf.format(LocalDateTime.now());
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("subtitle", "Created by JasperReports on: " + now);

            // Run Report; Get PDF Data
            
            byte[] pdf = new byte[0];

            if (reportFile != null) {
                pdf = JasperRunManager.runReportToPdf(reportFile, parameters, jasperDataSource);
                reportFile.close();
            }
            else {
                System.err.println("\n\nERROR: Report file missing or invalid!\n");
            }

            // Set Response Length; Send PDF Data to Client
            
            if (pdf.length > 0) {
                response.setContentLength(pdf.length);
                out.write(pdf);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Report Servlet";
    }

}

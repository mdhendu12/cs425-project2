package edu.jsu.mcis.project2;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Bean {
    private int num;

    private HashMap<String, String> parameters = null;

    public Bean() {
        parameters = new HashMap<>();
        num = 0;
    }
    
    public void incrementNum() {
        num++;
    }
    
    public void clear() {
        parameters.clear();
    }
    
    public String getParametersAsHTML() {

        StringBuilder s = new StringBuilder();

        for (HashMap.Entry<String, String> e : parameters.entrySet()) {

            s.append("<p>");
            s.append("Key: ").append(e.getKey()).append(", ");
            s.append("Value: ").append(e.getValue());
            s.append("</p>");

        }

        return s.toString();

    }
    
    public int getTerm() {
        return Integer.parseInt(parameters.get("term"));
    }
    
    public void setTerm(int term) {
        parameters.put("term", String.valueOf(term));
    }
    
    public String getSubject() {
        return parameters.get("subject");
    }
    
    public void setSubject(String subject) {
        parameters.put("subject", subject);
    }
    
    public int getCourseNumber() {
        return Integer.parseInt(parameters.get("courseNumber"));
    }
    
    public void setCourseNumber(int courseNumber) {
        parameters.put("courseNumber", String.valueOf(courseNumber));
    }
    
    public int getCourseLevel() {
        return Integer.parseInt(parameters.get("courseLevel"));
    }
    
    public void setCourseLevel(int courseLevel) {
        if (courseLevel != 0) {
            parameters.put("courseLevel", String.valueOf(courseLevel));
        }
    }
    
    public String getScheduleType() {
        return parameters.get("scheduleType");
    }
    
    public void setScheduleType(String scheduleType) {
        if (!scheduleType.equals("0")) {
            parameters.put("scheduleType", String.valueOf(scheduleType));
        }
    }
    
    public int getStarthour() {
        return Integer.parseInt(parameters.get("starthour"));
    }
    
    public void setStarthour(int starthour) {
        parameters.put("starthour", String.valueOf(starthour));
        setStart();
    }
    
    public int getStartmin() {
        return Integer.parseInt(parameters.get("startmin"));
    }
    
    public void setStartmin(int startmin) {
        parameters.put("startmin", String.valueOf(startmin));
        setStart();
    }
    
    public String getStartap() {
        return parameters.get("startap");
    }
    
    public void setStartap(String startap) {
        parameters.put("startap", String.valueOf(startap));
        setStart();
    }
    

    public int getEndhour() {
        return Integer.parseInt(parameters.get("endhour"));
    }
    
    public void setEndhour(int endhour) {
        parameters.put("endhour", String.valueOf(endhour));
        setEnd();
        
    }
    
    public int getEndmin() {
        return Integer.parseInt(parameters.get("endmin"));
    }
    
    public void setEndmin(int endmin) {
        parameters.put("endmin", String.valueOf(endmin));
        setEnd();
    }
    
    public String getEndap() {
        return parameters.get("endap");
    }
    
    public void setEndap(String endap) {
        parameters.put("endap", String.valueOf(endap));
        setEnd();
    }
    
    public void setStart() {
        String starthr = parameters.get("starthour");
        String startmin = parameters.get("startmin");
        String startap = parameters.get("startap");
        
        parameters.put("start", getLocalTimeString(starthr, startmin, startap));
    }
    
    public void setEnd() {
        String endhr = parameters.get("endhour");
        String endmin = parameters.get("endmin");
        String endap = parameters.get("endap");
        
        parameters.put("end", getLocalTimeString(endhr, endmin, endap));
    }
    
    public String getStart() {
        return parameters.get("start");
    }
    
    public String getEnd() {
        return parameters.get("end");
    }
    
    public String getLocalTimeString(String hr, String min, String ap) {
        String result = null;
        
        if ((hr != null) && (min != null) && (ap != null)) {
            if (!("0".equals(hr))) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                int hour = Integer.parseInt(hr);
                int minute = Integer.parseInt(min);
                if ("p".equals(ap)) {
                    hour += 12;
                }
                LocalTime time = LocalTime.of(hour, minute);
                result = dtf.format(time);
            }
        }
        return result;
    }
    
    public String getDaymon() {
        return parameters.get("daymon");
    }
    
    public void setDaymon(String daymon) {
        parameters.put("daymon", String.valueOf(daymon));
        this.setDays();
    }
    
    public String getDaytue() {
        return parameters.get("daytue");
    }
    
    public void setDaytue(String daytue) {
        parameters.put("daytue", String.valueOf(daytue));
        this.setDays();
    }
    
    public String getDaywed() {
        return parameters.get("daywed");
    }
    
    public void setDaywed(String daywed) {
        parameters.put("daywed", String.valueOf(daywed));
        this.setDays();
    }
    
    public String getDaythur() {
        return parameters.get("daythur");
    }
    
    public void setDaythur(String daythur) {
        parameters.put("daythur", String.valueOf(daythur));
        this.setDays();
    }
    
    public String getDayfri() {
        return parameters.get("dayfri");
    }
    
    public void setDayfri(String dayfri) {
        parameters.put("dayfri", String.valueOf(dayfri));
        this.setDays();
    }
    
    public HashMap<String, String> getParameters() {
        return this.parameters;
    }
    
    private void setDays() {
        String result = null;
        String[] days = {"daymon", "daytue", "daywed", "daythur", "dayfri"};
        StringBuilder sb = new StringBuilder();
        
        for (String day : days) {
            String dayLetter = parameters.get(day);
            if (dayLetter != null) {
                sb.append(dayLetter).append("|");
            }
        }
        result = sb.toString();
        
        if (result.length() >= 1) {
            parameters.put("days", result.substring(0, result.length() -1)) ;
        }
    }
    
}
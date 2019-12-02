/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import persistence.Course;
import persistence.Student;
import persistence.Request;

/**
 *
 * @author aman
 */
@Named(value = "acceptNewStudentsBean")
@ApplicationScoped
public class AcceptNewStudentsBean {
    @EJB
    private TeamFacadeLocal teamFacade;
    
    private ArrayList<Request> requests;
    private Student student;
    private Course course; 
    private List<Long> requestId;
    private Map<Long, Boolean> checkMap;
    private String statusGood;
    private String statusBad;
    
    public String getLink(){
        try{
            requests = new ArrayList<>();
            requestId = new ArrayList<>();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student student = (Student) session.getAttribute("User");
            requests = teamFacade.getRequests(student.getTeamId());
            for(Request request : requests){
                Request x = request;
                requests.add(new Request(x.getId(), x.getStatus(), x.getStudentId(), x.getTeamId()));
                requestId.add(request.getId());
            } 
            
            //checkboxes
            checkMap = new HashMap<>();
            // fill the check map up with <item, FALSE> values
            for (Long l : requestId) {
                checkMap.put(l, Boolean.FALSE);
            }
            
        }catch(Exception e){}  
        
        return "view_pending_requests";
    }
    
    public void acceptRequest(){
        
    }
    
    public ArrayList<Long> getSelected() {
        ArrayList<Long> result = new ArrayList<>();
        for (Map.Entry<Long, Boolean> entry : checkMap.entrySet()) {
            if (entry.getValue()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public Map<Long, Boolean> getCheckMap() {
        return checkMap;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public List<Long> getRequestId() {
        return requestId;
    }

    public void setRequestId(List<Long> requestId) {
        this.requestId = requestId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStatusGood() {
        return statusGood;
    }

    public void setStatusGood(String statusGood) {
        this.statusGood = statusGood;
    }

    public String getStatusBad() {
        return statusBad;
    }

    public void setStatusBad(String statusBad) {
        this.statusBad = statusBad;
    }
    
}

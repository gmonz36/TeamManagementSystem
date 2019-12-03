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
    @EJB
    private UserFacadeLocal userFacade;
    private List<Request> requestList;
    private ArrayList<Request> requests;
    private Student student; 
    private Map<String, Boolean> checkMap;
    private String statusGood;
    private String statusBad;
    
    public String redirect(){
        try{
            //empties the status
            statusBad = "";
            statusGood = "";
            //initializes the values
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student student = (Student) session.getAttribute("User");
            requests = new ArrayList<>();
            requestList = teamFacade.getRequests(student.getTeamId());
            
            //Adding the requests to the array
            for(Request request : requestList){
                Request x = request;
                requests.add(new Request(x.getId(), x.getStatus(), x.getStudentId(), x.getTeamId()));
            } 
            
            //redirecting to the right page
            if(teamFacade.isLiaison(student)){
                //checkboxes
                checkMap = new HashMap<>();
                for (Request r : requests) {
                    checkMap.put(r.getStudentId(), Boolean.FALSE);
                }
                return "accept_new_students";
            }else{
                return "view_pending_requests";
            }
            
        }catch(Exception e){
            return "menu";
        }     
    }
    
    public void acceptRequests(){
        try{
            //empties the status
            statusBad = "";
            statusGood = "";
            //initialize the variables
            int good=0, bad=0;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student student = (Student) session.getAttribute("User");
            ArrayList<String> selected = getSelected();
            
            Course course = teamFacade.findCourse(student.getSectionCode());

            //check the amount of team members (error)
            if((teamFacade.findTeamParams(course.getCourseCode())).getMaxStudents()<selected.size() + teamFacade.findCurrentAmountOfMembers(student.getTeamId())){
                statusBad = "With these requests the amount of team members exceeds the limit, please select less students";
                return; 
            }

            for (String studentId : selected) {
                Student s = (Student)userFacade.findStudent(studentId);
                if (s == null){
                    throw new Exception();      
                }
                //if the person is already in a team do not add it
                else if(s.getTeamId()!=null){
                    bad++;
                }else{
                    good++;
                    teamFacade.acceptStudent(s.getUserId(), student.getTeamId());
                }
            }   
            
            if(bad>0){
                statusBad = bad+" request(s) was/were not sent because you already have pending request with the team(s)";
            }
            if(good>0){
                statusGood = good+" request(s) was/were sent successfully";
            }
            
        }catch(Exception e){}
    }
    
    public ArrayList<String> getSelected() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : checkMap.entrySet()) {
            if (entry.getValue()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public Map<String, Boolean> getCheckMap() {
        return checkMap;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

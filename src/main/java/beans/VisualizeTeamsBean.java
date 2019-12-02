/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import persistence.Course;
import persistence.Instructor;
import persistence.Student;
import persistence.Team;

/**
 *
 * @author aman
 */
@Named(value = "visualizeTeamsBean")
@RequestScoped
public class VisualizeTeamsBean {
    @EJB
    private TeamFacadeLocal teamFacade;
    @EJB
    private UserFacadeLocal userFacade;
    
    private ArrayList<Team> teams;
    private HashMap<String, ArrayList<String>> members;
    
    /**
     * Creates a new instance of JoinTeamBean
     */
    public VisualizeTeamsBean() {
    }
    
    
    public String getCourseTeams(){
        try{
            teams = new ArrayList<>();
            members = new HashMap();
           
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Instructor ins = (Instructor) session.getAttribute("User");
            

            if((teamFacade.getTeams(userFacade.findCourseCode(ins.getUserId()).getCourseCode())!=null)){
                List<Team> resultList = teamFacade.getTeams(userFacade.findCourseCode(ins.getUserId()).getCourseCode());
                for(Team team : resultList){
                    Team x = team;
                    members.clear();
                    List<Student> students = userFacade.getStudentsInTeam(x.getTeamId());
                    ArrayList<String> studentIds = new ArrayList<>();
                    for (Student student: students){
                        studentIds.add(student.getUserId());
                    }
                    members.put(x.getTeamId(), studentIds);
                   
                    
                    
                    
                    teams.add(new Team(x.getTeamName(), x.getCourseCode(), x.getTeamId(), x.getDateOfCreation(), x.getTeamStatus(), x.getLiaisonId()));
                }
            }
        }catch(Exception e){
           System.out.println(e.getMessage());
        }  
        return "visualize_teams";
    }
    
   
    

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
    
    public HashMap getMembers() {
        return members;
    }

    public void setMembers(HashMap  members) {
        this.members = members;
    }
    
    
    public String getCourseCode() {
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Instructor ins = (Instructor) session.getAttribute("User");
   
        Course course = teamFacade.findCourse(ins.getSectionCode());
        return course.getCourseCode();
    }
}

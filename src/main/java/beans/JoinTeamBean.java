/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import persistence.Course;
import persistence.Student;
import persistence.Team;

/**
 *
 * @author aman
 */
@Named(value = "joinTeamBean")
@RequestScoped
public class JoinTeamBean {
    @EJB
    private TeamFacadeLocal teamFacade;
    
    private ArrayList<Team> teams;
    private Student student;
    private Course course; 
    
    /**
     * Creates a new instance of JoinTeamBean
     */
    public JoinTeamBean() {
    }
    
    public String getIncompleteTeam(){
        try{
            teams = new ArrayList<>();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student student = (Student) session.getAttribute("User");
            course = teamFacade.findCourse(student.getSectionCode());
            if(teamFacade.getTeams(course)!=null){
                List<Team> resultList = teamFacade.getTeams(course);
                for(Team team : resultList){
                    Team x = team;
                    teams.add(new Team(x.getCourseCode(), x.getTeamId(), x.getDateOfCreation(), x.getTeamStatus(), x.getLiaisonId()));
                }
            }
        }catch(Exception e){}  
        return "join_team";
    }
    
    public String sendRequests(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Student student = (Student) session.getAttribute("User");
        //createRequest("pending", student.getUserId(), teamId);
        return "create_team";
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
    
}

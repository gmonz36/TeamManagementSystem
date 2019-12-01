/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import persistence.Course;
import persistence.Instructor;
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

    
    /**
     * Creates a new instance of JoinTeamBean
     */
    public VisualizeTeamsBean() {
    }
    
    
    public void getCourseTeams(){
        try{
            teams = new ArrayList<>();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Instructor ins = (Instructor) session.getAttribute("User");
            
            if((teamFacade.getTeams(userFacade.findCourseCode(ins))!=null)){
                List<Team> resultList = teamFacade.getTeams(userFacade.findCourseCode(ins));
                for(Team team : resultList){
                    Team x = team;
                    teams.add(new Team(x.getCourseCode(), x.getTeamId(), x.getDateOfCreation(), x.getTeamStatus(), x.getLiaisonId()));
                }
            }
        }catch(Exception e){}  
    }
    
   
    

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
    
    
    public String getCourseCode() {
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Instructor ins = (Instructor) session.getAttribute("User");
   
        Course course = teamFacade.findCourse(ins.getSectionCode());
        return course.getCourseCode();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
public class JoinTeamBean {
    @EJB
    private TeamFacadeLocal teamFacade;
    
    private ArrayList<Team> teams;
    private Student student;
    private Course course; 
    private List<String> teamId = new ArrayList<>();
    private Map<String, Boolean> checkMap = new HashMap<>();
    
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
            if(teamFacade.getTeams(course.getCourseCode())!=null){
                List<Team> resultList = teamFacade.getTeams(course.getCourseCode());
                for(Team team : resultList){
                    Team x = team;
                    teams.add(new Team(x.getCourseCode(), x.getTeamId(), x.getDateOfCreation(), x.getTeamStatus(), x.getLiaisonId()));
                } 
            }
            
            //checkboxes
            for (Team team : teams){
                teamId.add(team.getTeamId());
            }
            // fill the check map up with <item, FALSE> values
            for (String s : teamId) {
                checkMap.put(s, Boolean.FALSE);
            }
            
        }catch(Exception e){}  
        
        return "join_team";
    }
    
    public String sendRequests(){
        System.out.println("HERE");
        getSelected();
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

    public List<String> getTeamId() {
        return teamId;
    }

    public void setTeamId(List<String> teamId) {
        this.teamId = teamId;
    }

    public String getSelected() {
        String result = "";
        for (Entry<String, Boolean> entry : checkMap.entrySet()) {
            if (entry.getValue()) {
                System.out.println("once");
                result = result + ", " + entry.getKey();
            }
        }
        if (result.length() == 0) {
            return "";
        } else {
            return result.substring(2);
        }
    }

    public Map<String, Boolean> getCheckMap() {
        return checkMap;
    }
    
}

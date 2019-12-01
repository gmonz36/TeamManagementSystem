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
        System.out.println("Entering getIncompleteTeams!!!!!!!!!");
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
                System.out.println(x.getCourseCode());
                } 
            }
            //checkboxes
            teamId = new ArrayList<String>();
            for (Team team : teams){
                teamId.add(team.getTeamId());
                System.out.println(team.getTeamId());
            }
            System.out.println(teamId.toString());
            // fill the check map up with <item, FALSE> values
            for (Team team : teams) {
                checkMap.put(team.getTeamId(), Boolean.FALSE);
            }
        }catch(Exception e){e.printStackTrace();}  
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
    
    private List<String> teamId;
    private Map<String, Boolean> checkMap = new HashMap<String, Boolean>();

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

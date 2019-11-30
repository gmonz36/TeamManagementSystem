/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import persistence.Student;
import persistence.User;

import persistence.Team;
import persistence.TeamParameters;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.ejb.EJB;
import persistence.Instructor;

/**
 *
 * @author Genevieve
 */
@Named(value = "createTeamBean")
@RequestScoped
public class CreateTeamBean {

    /**
     * Creates a new instance of SetUpTeamsParametersBean
     */
        @EJB
        private UserFacadeLocal userFacade;
        @EJB
        private TeamFacadeLocal teamFacade;
                
        private String teamId;
        private String teamName;
        private LocalDate dateOfCreation;
        private boolean teamStatus;
        private String liaisonId;
        private ArrayList<String> members;
        private String membersString;
        private String courseCode;
        

    @Resource
    private javax.transaction.UserTransaction utx;
    
    private String status;

    
    public CreateTeamBean() {
        
        
        
    }
    
     
    public void createTeam(){
        
        try {
            Team team = new Team();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student user = (Student)session.getAttribute("User");
            team.setLiaisonId(user.getUserId());

            team.setCourseCode(courseCode);
            TeamParameters params = teamFacade.findTeamParams(courseCode);


            if(params == null) {
                status="Invalid Course, please try again";
             } else {
            
                
                team.setDateOfCreation();
                team.setTeamName(teamName);
                
                
                membersString = membersString.replaceAll("\\s", ""); 
                
                String[] tmp_members = membersString.split(",");
 
                for (int i=0; i<tmp_members.length; i++){
                   
                    Student student = (Student)userFacade.findStudent(tmp_members[i]);
                    if (student == null){
                        throw new Exception();
                    }
                    else if(i>params.getMaxStudents()-1){
                        status="Number of team members exceeds limit";
                    }
                    else{
                        members.add(tmp_members[i]);
                    }
                }
                members.add(user.getUserId());
                
                        
                        
                team.setMembers(members);
                team.setTeamId();
                team.setCourseCode(courseCode);

                if(members.size()<params.getMaxStudents()){
                    team.set_teamStatus(false);
                }
                else{
                    team.set_teamStatus(true);
                }
               
                
                teamFacade.addTeam(team);
                status="team created";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            status="Error while creating team";
        }
  
        
        
    
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Student user = (Student)session.getAttribute("User");

        this.teamId = courseCode+user.getUserId(); 
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation() {
        this.dateOfCreation = java.time.LocalDate.now();
    }

    public boolean getTeamStatus () {
        return teamStatus;
    }

    public void set_teamStatus(boolean teamStatus) {
        this.teamStatus = teamStatus;
    }

    public String getLiaisonId() {
        return liaisonId;
    }

    public void setLiaisonId(String liaisonId) {
        this.liaisonId = liaisonId;
    }
    
    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
    
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    } 
      public String getMembersString() {
        return membersString;
    }

    public void setMembersString(String membersString) {
        this.membersString = membersString;
    } 
    
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}

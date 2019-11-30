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
        
        private UserFacadeLocal userFacade;
        
        private String teamId;
        private String teamName;
        private LocalDate dateOfCreation;
        private boolean teamStatus;
        private String liaisonId;
        private Student[] members;
        private String membersString;
        private String courseCode;
        
        
    @PersistenceContext(unitName = "TeamManagementSystemPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    
    private String status;

    
    public CreateTeamBean() {
        
        
        
    }
    
     public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
    public String createTeam(){
        
        try {
            Team team = new Team();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student user = (Student)session.getAttribute("User");
            team.setLiaisonId(user.getUserId());

            
            TeamParameters params = em.find(TeamParameters.class, courseCode);

            members = new Student[params.getMaxStudents()];

            if(params == null) {
                status="Invalid Course, please try again";
             } else {
            
                
                team.setDateOfCreation();
                team.setTeamName(teamName);
                
                
                membersString = membersString.replaceAll("\\s", ""); 
                
                
                String[] member_ids= membersString.split(",");
                if (member_ids.length+1>params.getMaxStudents()) {
                    status="Number of team members exceeds limit";
                    return "Error";
                }
                
                members[0] = user;
                for (int i=1; i<=member_ids.length; i++){
                    Student student = (Student)userFacade.findById(member_ids[i]);
                    members[i] = student;
                }
                
                        
                        
                team.setMembers(members);
                team.setTeamId();
                team.setCourseCode(courseCode);

                if(members.length+1<params.getMaxStudents()){
                    team.set_teamStatus(false);
                }
                else{
                    team.set_teamStatus(true);
                }
               
                
                persist(team);

                FacesContext.getCurrentInstance().getExternalContext().redirect("faces/student_protected/create_team.xhtml");
            }
        } catch(IOException ex) {
            return "error";
        }
        return "confirm";
        
        
    
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId() {
        this.teamId = courseCode+""; //TODO add number num of teams in course + 1
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
    
    public Student[] getMembers() {
        return members;
    }

    public void setMembers(Student[] members) {
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
    
    
    
}

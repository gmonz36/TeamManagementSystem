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
import persistence.Team;
import persistence.TeamParameters;
import java.time.LocalDate;
import persistence.Instructor;

/**
 *
 * @author Mikael
 */
@Named(value = "createTeamBean")
@RequestScoped
public class CreateTeamBean {

    /**
     * Creates a new instance of SetUpTeamsParametersBean
     */
         
        private String teamId;
        private String teamName;
        private LocalDate dateOfCreation;
        private boolean teamStatus;
        private String liaisonId;
        private Student[] members;
        
        
        
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
            
            TeamParameters params = em.find(TeamParameters.class, courseCode);

            if(params == null) {
                status="Invalid Course, please try again";
             } else {
            
   
                if (members.length>params.getMaxStudents()) {
                    status="Number of team members exceeds limit";
                    return "Error";
                }
                team.set_dateOfCreation();
                team.set_teamName(teamName);


                tp.setMaxStudents(max_students);
                tp.setDeadline(deadline);
                tp.setCourseCode(courseCode);

                if(members.length+1<params.getMaxStudents()){
                    team.set_teamStatus(false);
                }
                else{
                    team.set_teamStatus(true);
                }
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                Student user = (Student)session.getAttribute("User");
                team.set_liaisonId(user.getUserId());

                
                persist(tp);

                FacesContext.getCurrentInstance().getExternalContext().redirect("faces/instructor_protected/visualize_teams.xhtml");
            }
        } catch(IOException ex) {
            return "error";
        }
        return "confirm";
        
        
    
    }

    public String get_teamId() {
        return teamId;
    }

    public void set_teamId(String teamId) {
        this.teamId = teamId;
    }

    public String get_teamName() {
        return teamName;
    }

    public void set_teamName(String teamName) {
        this.teamName = teamName;
    }

    public LocalDate get_dateOfCreation() {
        return dateOfCreation;
    }

    public void set_dateOfCreation() {
        this.dateOfCreation = java.time.LocalDate.now(); // TODO set current date
    }

    public boolean get_teamStatus () {
        return teamStatus;
    }

    public void set_teamStatus(boolean teamStatus) {
        this.teamStatus = teamStatus;
    }

    public String get_liaisonId() {
        return liaisonId;
    }

    public void set_liaisonId(String liaisonId) {
        this.liaisonId = liaisonId;
    }
    
    public Student[] get_members() {
        return members;
    }

    public void set_members(Student[] members) {
        this.members = members;
    }
    
    
    
    
    
}

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
import persistence.TeamParameters;

/**
 *
 * @author Mikael
 */
@Named(value = "setUpTeamsParametersBean")
@RequestScoped
public class SetUpTeamsParametersBean {

    /**
     * Creates a new instance of SetUpTeamsParametersBean
     */
    
        private int min_students;
        private int max_students;
        private String deadline;
        private String courseCode;
        
    @PersistenceContext(unitName = "TeamManagementSystemPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    
    private String status;
    
    public SetUpTeamsParametersBean() {
        
        
        
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
    
    public String confirmParameters(){
        
        
        try {
            
            TeamParameters tp = new TeamParameters();
            if (min_students>max_students) {
                status="max_students cannot be smaller tha min_students";
                return "Error";
            }
            tp.setMinStudents(min_students);
            tp.setMaxStudents(max_students);
            tp.setDeadline(deadline);
            tp.setCourseCode(courseCode);
            persist(tp);
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/instructor_protected/visualize_teams.xhtml");
        } catch(IOException ex) {
            return "error";
        }
        return "confirm";
        
        
    
    }

    public int getMin_students() {
        return min_students;
    }

    public void setMin_students(int min_students) {
        this.min_students = min_students;
    }

    public int getMax_students() {
        return max_students;
    }

    public void setMax_students(int max_students) {
        this.max_students = max_students;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
}

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
import javax.ejb.EJB;
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
    
    @EJB
    private TMSFacadeLocal objectFacade;
    private int min_students;
    private int max_students;
    private String deadline;
    private String courseCode;
    private String status;
    
    /**
     * Creates a new instance of SetUpTeamsParametersBean
     */
    public SetUpTeamsParametersBean() {
    }
    
    public String confirmParameters(){
        try {
            
            TeamParameters tp = new TeamParameters();
            if (min_students>max_students) {
                status="max_students cannot be smaller tha min_students";
                return "Error";
            }
            objectFacade.addTeamParams(courseCode, min_students, max_students, deadline);
            
            status = "Team parameters setup succesfully";
        } catch(Exception ex) {
            status = "Could not setup team parameters";
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

package beans;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import persistence.Instructor;
import persistence.Student;

@Named(value = "logInBean")
@RequestScoped
public class LogInBean {
    private String userId;
    private String password;
    private String status;
    @EJB
    private TMSFacadeLocal objectFacade;      
    
    /**
     * Creates a new instance of LoginBean
     */
    public LogInBean() {
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    public void login() {
        try {
            if (objectFacade.isValidLogin(userId, password)) {
                //login ok - set user in session context
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                if(objectFacade.findStudent(userId)!=null) {
                    session.setAttribute("User", objectFacade.findStudent(userId));
                    FacesContext.getCurrentInstance().getExternalContext().redirect("faces/student_protected/menu.xhtml");


                } else {
                   session.setAttribute("User", objectFacade.findInstructor(userId)); 
                   FacesContext.getCurrentInstance().getExternalContext().redirect("faces/instructor_protected/menu.xhtml");

                }

            } else {
                status="Invalid Login, Please Try again"; 
            }
        } catch (Exception ex) {
            Logger.getLogger(LogInBean.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public String logout() {
        // invalidate session to remove User
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/TeamManagementSystem/");
        } catch(IOException ex) {
            return "error";
        }
        return "logout";
    }
    
}

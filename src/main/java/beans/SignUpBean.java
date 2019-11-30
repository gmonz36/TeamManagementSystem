package beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "signUpBean")
@RequestScoped
public class SignUpBean {
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String type;
    private String program;
    @EJB
    private UserFacadeLocal userFacade;    
    @Resource
    private javax.transaction.UserTransaction utx;
    
    private String status;
    /**
     * Creates a new instance of SignInBean
     */
    public SignUpBean() {
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
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }    

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    } 
    
    /**
     * @return the program
     */
    public String getProgram() {
        return program;
    }

    /**
     * @param program the program to set
     */
    public void setProgram(String program) {
        this.program = program;
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
     * @return the status message
     */
    public String getStatus() {
        return status;
    }
    
    public void addUser() {
        try {
            if(type.equals("Student")) {
                if(program.equals("")) {
                    status="Students must specify their program.";
                    return;
                }
                userFacade.addStudent(program, userId, firstname, lastname, password);
                status="New Student Created Fine"; 
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
            else if(type.equals("Instructor")) {
                userFacade.addInstructor(userId, firstname, lastname, password);
                status="New Instructor Created Fine"; 
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } else {
                throw new RuntimeException("User type not defined.");
            }
        } catch (Exception ex ) {
            Logger.getLogger(SignUpBean.class.getName()).log(Level.SEVERE, null, ex);
            status="Error While Creating New User";
        }
    }
    
}

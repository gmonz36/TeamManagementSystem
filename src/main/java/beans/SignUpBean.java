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
    private String sectionCode;
    private String courseCode;
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
    
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * @param sectionCode the sectionCode to set
     */
    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }
    
    
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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
                sectionCode = userFacade.getSectionCode();
                
                userFacade.addStudent(program, userId, firstname, lastname, password, sectionCode);
                status="New Student Created Fine"; 
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
            else if(type.equals("Instructor")) {
                courseCode = userFacade.getCourseCode();
                if (userFacade.findCourseInstructor(courseCode) != null){
                    status="Instructor for course already exists"; 
                }
                else{
                    userFacade.addInstructor(userId, firstname, lastname, password, courseCode);
                    status="New Instructor Created Fine"; 
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                }
                
            } else {
                throw new RuntimeException("User type not defined.");
            }
        } catch (Exception ex ) {
            Logger.getLogger(SignUpBean.class.getName()).log(Level.SEVERE, null, ex);
            status="Error While Creating New User";
        }
    }
    
}

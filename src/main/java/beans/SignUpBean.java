package beans;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.User;
import persistence.Student;
import persistence.Instructor;

/**
 *
 * @author ssome
 */
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
    @PersistenceContext(unitName = "TeamManagementSystemPU")
    private EntityManager em;
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
    
    public void addUser() {
        try {
            if(type.equals("Student")) {
                Student acc = new Student();
                if(program.equals("")) {
                    status="Students must specify their program.";
                    return;
                }
                acc.setProgram(program);
                acc.setUserId(userId);
                acc.setFirstname(firstname);
                acc.setLastname(lastname);;
                // randomly generate salt value
                final Random r = new SecureRandom();
                byte[] salt = new byte[32];
                r.nextBytes(salt);
                String saltString = new String(salt, "UTF-8");
                // hash password using SHA-256 algorithm
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String saltedPass = saltString+password;
                byte[] passhash = digest.digest(saltedPass.getBytes("UTF-8"));
                acc.setSalt(salt);
                acc.setPassword(passhash);
                persist(acc);
                status="New Student Created Fine";                
            }
            else if(type.equals("Instructor")) {
                Instructor acc = new Instructor();
                acc.setUserId(userId);
                acc.setFirstname(firstname);
                acc.setLastname(lastname);;
                // randomly generate salt value
                final Random r = new SecureRandom();
                byte[] salt = new byte[32];
                r.nextBytes(salt);
                String saltString = new String(salt, "UTF-8");
                // hash password using SHA-256 algorithm
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String saltedPass = saltString+password;
                byte[] passhash = digest.digest(saltedPass.getBytes("UTF-8"));
                acc.setSalt(salt);
                acc.setPassword(passhash);
                persist(acc);
                status="New Instructor Created Fine";                
            } else {
                throw new RuntimeException("User type not defined.");
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | RuntimeException ex ) {
            Logger.getLogger(SignUpBean.class.getName()).log(Level.SEVERE, null, ex);
            status="Error While Creating New User";
        }
    }
    
}

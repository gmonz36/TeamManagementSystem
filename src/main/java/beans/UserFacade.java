/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.security.MessageDigest;
import java.security.SecureRandom;
import persistence.User;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import persistence.Student;
import persistence.Instructor;

/**
 *
 * @author ssome
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {
    @PersistenceContext(unitName = "TeamManagementSystemPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public void addStudent(String program, String userId, String firstname,
            String lastname, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            Student acc = new Student();
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
            create(acc);
    }
    
    @Override
    public void addInstructor(String userId, String firstname,
            String lastname, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
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
            create(acc);
    }
    
    @Override
    public boolean isValidLogin(String userId, String password)
                    throws UnsupportedEncodingException, NoSuchAlgorithmException{
        
        Student acc = em.find(Student.class, userId);
        if (acc != null) {
            // check password
            byte[] salt = acc.getSalt();
            String saltString = new String(salt, "UTF-8");
            String checkPass = saltString+password;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] checkPassHash = digest.digest(checkPass.getBytes("UTF-8"));
            if (Arrays.equals(checkPassHash, acc.getPassword())) {
                return true;
            } else {
                return false;
            }             
         } else {
             Instructor ins = em.find(Instructor.class, userId);
             if(ins == null) {
                return false;
             } else {
                // check password
                byte[] salt = ins.getSalt();
                String saltString = new String(salt, "UTF-8");
                String checkPass = saltString+password;
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] checkPassHash = digest.digest(checkPass.getBytes("UTF-8"));
                if (Arrays.equals(checkPassHash, ins.getPassword())) {
                    return true;
                } else {
                    return false; 
                }
             }
         }    
    }
    @Override
    public Student findStudent(Object id) {
        return getEntityManager().find(Student.class, id);

    }
    @Override
    public Instructor findInstructor(Object id) {
        return getEntityManager().find(Instructor.class, id);

    }
    

}
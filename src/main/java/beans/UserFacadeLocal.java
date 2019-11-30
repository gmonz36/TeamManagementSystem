/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import persistence.User;
import persistence.Student;
import persistence.Instructor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ssome
 */
@Local
public interface UserFacadeLocal {

    public void addStudent(String program, String userId, String firstname,
            String lastname, String password) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    public void addInstructor(String userId, String firstname,
            String lastname, String password) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;
    
    public boolean isValidLogin(String userId, String password) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;
            
    void edit(User user);

    void remove(User user);

    Student findStudent(Object id);
    
    Instructor findInstructor(Object id);
    
    List<User> findAll();

    List<User> findRange(int[] range);

    int count();
    
    
    
}

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
import persistence.Course;
import persistence.CourseSection;

/**
 *
 * @author ssome
 */
@Local
public interface UserFacadeLocal {

    public void addStudent(String program, String userId, String firstname,
            String lastname, String password, String sectionCode, String email) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    public void addInstructor(String userId, String firstname,
            String lastname, String password, String email) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;
    
    public boolean isValidLogin(String userId, String password) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;
            
    void edit(User user);
    void editStudent(Student student);

    
    void remove(User user);

    Student findStudent(Object id);
    
    Instructor findInstructor(Object id);
    
    List<User> findAll();

    List<User> findRange(int[] range);

    int count();
    
    public Course findCourseCode(String id);
    public List<Student> getStudentsInTeam(String teamId);

    public String getSectionCode();

    public String getCourseCode();

    public String findCourseInstructor(String courseCode);

    public void modifyCourse(String userId);

    public void addCourseAndSection(Course course, CourseSection section);


    public int countCourses();

}

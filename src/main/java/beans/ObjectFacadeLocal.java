/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Local;
import persistence.Course;
import persistence.CourseSection;
import persistence.Instructor;
import persistence.Request;
import persistence.Student;
import persistence.Team;
import persistence.TeamParameters;
/**
 *
 * @author aman
 */
@Local
public interface ObjectFacadeLocal {
    
    /*
    * student
    */
    public void addStudent(String program, String userId, String firstname,
            String lastname, String password, String sectionCode, String email) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;
    Student findStudent(Object id);
    public List<Student> getStudentsInTeam(String teamId);
    void editStudent(Student student);
    public boolean isLiaison(Student student);

    /*
    * Instructor
    */
    public void addInstructor(String userId, String firstname,
            String lastname, String password, String email) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;
    Instructor findInstructor(Object id);
    public String findCourseInstructor(String courseCode);

    
    /*
    * Course and course section
    */
    public void addCourseAndSection(Course course, CourseSection section);
    public Course findCourseCode(String id);
    public String getSectionCode();
    public String getCourseCode();
    public void modifyCourse(String userId);
    public int countCourses();
    public Course findCourse(String sectionCode);
    
    /*
    * Request
    */
    public void acceptStudent(String studentId, String teamId);
    public void createRequest(String status, String userId, String teamId);
    public List<Request> getRequests (String teamId);
    public boolean requestExists(String studentId, String teamId);

    /*
    * Team
    */
    void addTeam(Team team);
    public List<Team> getTeams(String courseCode);
    public List<Team> getIncompleteTeams(String courseCode);
    public boolean teamNameAlreadyExists(String teamName);
    
    /*
    * Team Params
    */
    TeamParameters findTeamParams(Object courseCode);
    public void addTeamParams(String courseCode,int min_students,int max_students,String deadline);
    
    /*
    * other
    */
    public boolean isValidLogin(String userId, String password) 
            throws UnsupportedEncodingException, NoSuchAlgorithmException;
    public int findCurrentAmountOfMembers(String teamId);
      
    /*
    *
    */
    void create(Object obj);
    void edit(Object obj);
    void remove(Object obj);
    Object find(Object id);
    List<Object> findAll();
    List<Object> findRange(int[] range);
    int count();
    
}

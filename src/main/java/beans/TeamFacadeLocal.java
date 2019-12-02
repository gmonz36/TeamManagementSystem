/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import persistence.Team;
import java.util.List;
import javax.ejb.Local;
import persistence.TeamParameters;
import persistence.Course;
import persistence.Request;
import persistence.Student;

/**
 *
 * @author ssome
 */
@Local
public interface TeamFacadeLocal {


    void addTeam(Team team);

    void create(Object user);

    void edit(Object user);

    void remove(Object user);


    Object find(Object id);

    List<Object> findAll();

    List<Object> findRange(int[] range);

    int count();

    TeamParameters findTeamParams(Object courseCode);

    public void addTeamParams(String courseCode,int min_students,int max_students,String deadline);

    
    public List<Team> getTeams(String courseCode);
            
    public List<Team> getIncompleteTeams(String courseCode);
    
    public Course findCourse(String sectionCode);
    
    public void createRequest(String status, String userId, String teamId);
    
    public boolean requestExists(String studentId, String teamId);
    
    public boolean teamNameAlreadyExists(String teamName);
    
    public boolean isLiaison(Student student);
    
    public List<Request> getRequests (String teamId);
    
    public void acceptStudent(String studentId, String teamId);
    
}

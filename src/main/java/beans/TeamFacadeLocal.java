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

    
    public List<Team> getTeams(Course course);
            
    public List<Team> getIncompleteTeams(String courseCode);
    
    public Course findCourse(String sectionCode);
    
    public void createRequest(String status, String userId, String teamId);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.util.ArrayList;
import persistence.Team;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import persistence.Student;
import persistence.Course;
import persistence.CourseSection;
import persistence.Request;
import persistence.TeamParameters;

/**
 *
 * @author ssome
 */
@Stateless
public class TeamFacade extends AbstractFacade<Object> implements TeamFacadeLocal {
    @PersistenceContext(unitName = "TeamManagementSystemPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamFacade() {
        super(Object.class);
    }  
    
    @Override
    public void addTeamParams(String courseCode,int min_students,int max_students,String deadline){
            TeamParameters tp = new TeamParameters();
            tp.setCourseCode(courseCode);
            tp.setMinStudents(min_students);
            tp.setMaxStudents(max_students);
            tp.setDeadline(deadline);
            create(tp);
    }



    
    public void addTeam(Team team) {
        getEntityManager().persist(team);
        

    }
    
    
    @Override
    public TeamParameters findTeamParams(Object courseCode) {
        return getEntityManager().find(TeamParameters.class, courseCode);

    }

    


    @Override
    //Returns list of all teams from a class
    public List<Team> getTeams(String courseCode){
        Query query = em.createQuery(
                "SELECT u FROM Team u" +
                " WHERE u.courseCode = :CourseCode");
            query.setParameter("CourseCode",courseCode);
            List resultList = query.getResultList();
            return resultList;
    }
    
    @Override
    //Returns list of incomplete teams in specific class
    public List<Team> getIncompleteTeams(String courseCode){
        
        Query query = em.createQuery(
                "SELECT u FROM Team u" +
                " WHERE u.courseCode = :CourseCode" +
                        " AND u.teamStatus = :TeamStatus");
            query.setParameter("CourseCode",courseCode);
            query.setParameter("TeamStatus","Incomplete");
            List resultList = query.getResultList();
            return resultList;
        
        
        
    }
    
    @Override
    //Find course related to section
    public Course findCourse(String sectionCode){
    try {
            Query query = em.createQuery(
                "SELECT u.courseCode FROM CourseSection u" +
                " WHERE u.sectionCode = :CourseSectionCode");
            query.setParameter("CourseSectionCode",sectionCode);
            String courseCode = (String) query.getSingleResult();
            
            return getEntityManager().find(Course.class, courseCode);

            
        } catch (Exception e) {
        }
        return null;
    }
    @Override
    //Creates a Request
    public void createRequest(String status, String userId, String teamId){
        Request re = new Request();
            re.setStatus(status);
            re.setStudentId(userId);
            re.setTeamId(teamId);
            create(re);   
    }
    
}

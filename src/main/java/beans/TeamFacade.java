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
    
    @Override
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
                        " AND u.teamStatus <> :TeamStatus");
            query.setParameter("CourseCode",courseCode);
            query.setParameter("TeamStatus","Complete");
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
    
    @Override
    //Checks if requests already exists
    public boolean requestExists(String studentId, String teamId){
    
        try{
         Query query = em.createQuery(
                "SELECT u FROM Request u" +
                " WHERE u.studentId = :StudentID" + 
                " AND u.teamId = :TeamID");
            query.setParameter("StudentID",studentId);
            query.setParameter("TeamID",teamId);
            
            if(query.getResultList().isEmpty()){
                return false;
            }else{
                return true;
            }                          
        }
        catch(Exception e){
             System.out.println(e.getMessage());
        }
        return true;
    }
    
    @Override
    //Checks if team name is duplicate
    public boolean teamNameAlreadyExists(String teamName){
    
         try{
         Query query = em.createQuery(
                "SELECT u FROM Team u" +
                " WHERE u.teamName = :TeamName");
            query.setParameter("TeamName",teamName);
            
            if(query.getResultList().isEmpty()){
                return false;
            }else{
                return true;
            }     
        }
        catch(Exception e){
        }
        return true;
    }
    
    @Override
    //Checks if student is his team's liaison
    public boolean isLiaison(Student student){

    Query query = em.createQuery(
            "SELECT u FROM Team u" +
            " WHERE u.liaisonId = :LiaisonId" +
            " AND u.teamId = :TeamId");
        query.setParameter("LiaisonId",student.getUserId());
        query.setParameter("TeamId",student.getTeamId());

        if(query.getResultList().isEmpty()){
                return false;
            }else{
                return true;
            }  
        
    }
    
    @Override
    //Gets the list of requests pending associated with a team
    public List<Request> getRequests (String teamId){
        
        Query query = em.createQuery(
                "SELECT u FROM Request u" +
                " WHERE u.teamId = :TeamId");
            query.setParameter("TeamId",teamId);
            List resultList = query.getResultList();
            return resultList;
        
            
    }
    
    @Override
    //Accepts a student into a team
    public void acceptStudent(String studentId, String teamId){
        
         Query query = em.createQuery(
                "UPDATE Request u " +
                "SET u.status ='Accepted'"+
                " WHERE u.teamId =:TeamID" +
                " AND u.studentId = :StudentID");
            query.setParameter("StudentID",studentId);
            query.setParameter("TeamID",teamId);
            query.executeUpdate();
            
         Query query2 = em.createQuery(
                "UPDATE Student u " +
                "SET u.teamId =:StudentTeamID"+
                " WHERE u.userId = :StudentID");
            query2.setParameter("StudentID",studentId);
            query2.setParameter("StudentTeamID",teamId);
            query2.executeUpdate();
        
    }
    
    @Override
    //Gets total members in a team
    public int findCurrentAmountOfMembers(String teamId){
        
         Query query = em.createQuery(
                "SELECT COUNT(u) FROM Student u" +
                " WHERE u.teamId = :TeamId");
            query.setParameter("TeamId",teamId);
            
            System.out.println("AAAAAAAAAAAA");
            System.out.println(query.getSingleResult());
            
            return (int) query.getSingleResult();
    
    }
    
    
}

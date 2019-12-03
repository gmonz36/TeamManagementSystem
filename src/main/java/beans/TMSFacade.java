/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import persistence.Instructor;
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
 * @author aman
 */
@Stateless
public class TMSFacade extends AbstractFacade<Object> implements TMSFacadeLocal {
    @PersistenceContext(unitName = "TeamManagementSystemPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TMSFacade() {
        super(Object.class);
    }

    @Override
    public void addStudent(String program, String userId, String firstname,
        String lastname, String password, String sectionCode, String email) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Student acc = new Student();
        acc.setProgram(program);
        acc.setEmail(email);
        acc.setSectionCode(sectionCode);
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
        String lastname, String password, String email) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Instructor acc = new Instructor();
        acc.setUserId(userId);
        acc.setEmail(email);
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
    public Course findCourseCode(String id){
        try {
            Query query = em.createQuery(
                "SELECT u.courseCode FROM Course u" +
                " WHERE u.instructorId = :InsID");
            query.setParameter("InsID",id);
            String courseCode = (String) query.getSingleResult();
            
            return getEntityManager().find(Course.class, courseCode);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        return null;      
    
    }
    
    @Override
    public String findCourseInstructor(String courseCode){
        try {
            Query query = em.createQuery(
                "SELECT u.instructorId FROM Course u" +
                " WHERE u.courseCode = :code");
            query.setParameter("code",courseCode);
            String instructorId = (String) query.getSingleResult();
            
            return instructorId;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());         
        }
        return null;
        
    
    } 
    
    @Override
    public Instructor findInstructor(Object id) {
        return getEntityManager().find(Instructor.class, id);
    
    }
    @Override
    public void editStudent(Student student){
        try {
            Query query = em.createQuery(
                "UPDATE Student u " +
                "SET u.teamId =:StudentTeamID"+
                " WHERE u.userId = :StudentID");
            query.setParameter("StudentID",student.getUserId());
            query.setParameter("StudentTeamID",student.getTeamId());
            query.executeUpdate();
      
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    @Override
    public List<Student> getStudentsInTeam(String teamId){
        
        Query query = em.createQuery(
                "SELECT u FROM Student u" +
                " WHERE u.teamId = :TeamID");
            query.setParameter("TeamID",teamId);
            List resultList = query.getResultList();
            return resultList;
        
        
        
    }
    
    @Override
    public String getSectionCode(){
        try {
            Query query = em.createQuery(
                "SELECT u.sectionCode FROM CourseSection u"
               );
          
            String sectionCode = (String) query.getSingleResult();
            
            return sectionCode;

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        return null;
        
    }
    
    @Override
    public String getCourseCode(){
        try {
            Query query = em.createQuery(
                "SELECT u.courseCode FROM Course u"
               );
          
            String courseCode = (String) query.getSingleResult();
            
            return courseCode;

            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        return null;
    }
    
    
    @Override
    public void modifyCourse(String userId){
     try {
            Query query = em.createQuery(
                "UPDATE Course " +
                "SET instructorId = :InstructorID");
            query.setParameter("InstructorID", userId);
            query.executeUpdate();
      
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
 
    @Override
    public void addCourseAndSection(Course course, CourseSection section){
        getEntityManager().persist(course);
        getEntityManager().persist(section);
    }

    @Override
    public int countCourses(){
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Course> rt = cq.from(Course.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    
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
        System.out.println("And you do actually pass by here right???");
        System.out.println(teamId);
        System.out.println(studentId);
        
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
            
            return Integer.parseInt(query.getSingleResult().toString());
    
    }                       

    



}

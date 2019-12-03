/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import persistence.Student;

import persistence.Team;
import persistence.TeamParameters;
import java.time.LocalDate;
import javax.ejb.EJB;
import persistence.Course;

/**
 *
 * @author Genevieve
 */
@Named(value = "createTeamBean")
@RequestScoped
public class CreateTeamBean {

    /**
     * Creates a new instance of SetUpTeamsParametersBean
     */
    @EJB
    private ObjectFacadeLocal objectFacade;
    
    private String teamName;
    private String membersString;
    private String status;

    /*
    * Constructors
    */
    public CreateTeamBean() { 
    }
       
    public void createTeam(){
        try {
            //we get the courseCode of the student
            String courseCode=getCourseCode();
            
            //session attributes
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student user = (Student)session.getAttribute("User");
            
            //verify that TeamParameters exist
            TeamParameters params = objectFacade.findTeamParams(courseCode);
            if(params == null) {
                status = "No Team parameters exist for this course";
                return;
            }
                
            //putting all the studentId in a String[]
            membersString = membersString.replaceAll("\\s", ""); 
            String[] tmp_members = null;
            //Check for error
            if (!membersString.equals("")){
                 tmp_members = membersString.split(",");
            }
            
            //checks if Logged In student already in a team (error 1.1)
            if (user.getTeamId()!=null){                    
                    status = "You are already in a team";
                    return;
            }
            
            //checks if student is already in team (error 1.2)
            if (tmp_members != null){
                for (int i=0; i<tmp_members.length; i++){
                    Student student = (Student)objectFacade.findStudent(tmp_members[i]);
                    if (student == null){
                        throw new Exception();      
                    }
                    else if(student.getTeamId()!=null){
                        status = "One of the students is already in a team";
                        return;
                    }
                }
            }

            //checks for a duplicate team name (error 2)
            if(objectFacade.teamNameAlreadyExists(teamName)){
                status = "This team name already exists";
                return;
            }
            
            //checks for the number of students (error 3)
            if(tmp_members !=null && tmp_members.length>params.getMaxStudents()-1){
                status = "Number of team members exceeds limit";
                return;
            }

            
            

            String teamId = getCourseCode()+user.getUserId();

            //if we encountered no error
            //goes through all the members in the list and add the teamId
            if (tmp_members!=null){
                for (int i=0; i<tmp_members.length; i++){
                    Student student = (Student)objectFacade.findStudent(tmp_members[i]);
                    student.setTeamId(teamId);
                    objectFacade.editStudent(student);
                }
            }

            user.setTeamId(teamId);
            objectFacade.editStudent(user);

            //set the Team variables
            Team team = new Team();
            team.setLiaisonId(user.getUserId());
            team.setDateOfCreation();
            team.setTeamName(teamName);
            team.setTeamId(getCourseCode()+user.getUserId());
            team.setCourseCode(courseCode);

            //set the status
            int studentTotal;
            if (tmp_members != null) studentTotal = tmp_members.length+1;
            else studentTotal = 1;


            if(studentTotal<params.getMinStudents()){
                team.setTeamStatus("Incomplete");
            }else if(studentTotal<params.getMaxStudents()){
                team.setTeamStatus("Valid");
            }else{
                team.setTeamStatus("Complete");
            }


            objectFacade.addTeam(team);
            status="team created";


        } catch(Exception ex) {
            ex.printStackTrace();
            status="Error while creating team";
        }
  
    
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    
    
    public String getCourseCode() {
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Student student = (Student) session.getAttribute("User");
   
        Course course = objectFacade.findCourse(student.getSectionCode());
        return course.getCourseCode();
    }


      public String getMembersString() {
        return membersString;
    }

    public void setMembersString(String membersString) {
        this.membersString = membersString;
    } 
    
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}

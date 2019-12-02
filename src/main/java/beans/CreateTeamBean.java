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
    private UserFacadeLocal userFacade;
    @EJB
    private TeamFacadeLocal teamFacade;
    
    private String teamId;
    private String teamName;
    private LocalDate dateOfCreation;
    private boolean teamStatus;
    private String liaisonId;
    private String membersString;
    private String courseCode;
    private String status;

    /*
    * Constructors
    */
    public CreateTeamBean() { 
    }
       
    public void createTeam(){
        try {
            //we get the courseCode of the student
            courseCode=getCourseCode();
            
            //session attributes
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student user = (Student)session.getAttribute("User");
            
            //verify that TeamParameters exist
            TeamParameters params = teamFacade.findTeamParams(courseCode);
            if(params == null) {
                status = "No Team parameters exist for this course";
            } else {
                
                boolean flag = true;
                //putting all the studentId in a String[]
                membersString = membersString.replaceAll("\\s", ""); 
                
                //memberString is not null so the problem is probably at the split
                String[] tmp_members = membersString.split(",");
                
                System.out.println(tmp_members[0]);
                //checks for the number of students (error 3)
                if(tmp_members.length>params.getMaxStudents()-1){
                    flag = false;
                    status = "Number of team members exceeds limit";
                }
                //checks for a duplicate team name (error 2)
                if(teamFacade.teamNameAlreadyExists(teamName)){
                    flag = false;
                    status = "This team name already exists";
                }
                //checks if student is already in team (error 1)
                for (int i=0; i<tmp_members.length; i++){
                    Student student = (Student)userFacade.findStudent(tmp_members[i]);
                    if (student == null){
                        throw new Exception();      
                    }
                    else if(student.getTeamId()!=null){
                        flag = false;
                        status = "One of the students is already in a team";
                    }
                }
                
                //if we encountered no error
                if (flag){
                    //goes through all the members in the list and add the teamId
                    for (int i=0; i<tmp_members.length; i++){
                        Student student = (Student)userFacade.findStudent(tmp_members[i]);
                        student.setTeamId(getCourseCode()+user.getUserId());
                        userFacade.editStudent(student);
                    }
                    
                    user.setTeamId( getCourseCode()+user.getUserId());
                    userFacade.editStudent(user);

                    //set the Team variables
                    Team team = new Team();
                    team.setLiaisonId(user.getUserId());
                    team.setDateOfCreation();
                    team.setTeamName(teamName);
                    team.setTeamId(getCourseCode()+user.getUserId());
                    team.setCourseCode(courseCode);
                    //set the status
                    if(tmp_members.length+1<params.getMinStudents()){
                        team.setTeamStatus("Incomplete");
                    }else if(tmp_members.length+1<params.getMaxStudents()){
                        team.setTeamStatus("Valid");
                    }else{
                        team.setTeamStatus("Complete");
                    }


                    teamFacade.addTeam(team);
                    status="team created";
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            status="Error while creating team";
        }
  
        
        
    
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {


        this.teamId = teamId;
       
        
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation() {
        this.dateOfCreation = java.time.LocalDate.now();
    }

    public boolean getTeamStatus () {
        return teamStatus;
    }

    public void set_teamStatus(boolean teamStatus) {
        this.teamStatus = teamStatus;
    }

    public String getLiaisonId() {
        return liaisonId;
    }

    public void setLiaisonId(String liaisonId) {
        this.liaisonId = liaisonId;
    }
    
    
    public String getCourseCode() {
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Student student = (Student) session.getAttribute("User");
   
        Course course = teamFacade.findCourse(student.getSectionCode());
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

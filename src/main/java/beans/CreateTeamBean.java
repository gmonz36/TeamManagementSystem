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



    @Resource
    private javax.transaction.UserTransaction utx;
    
    private String status;

    
    public CreateTeamBean() {
        
        
        
    }
   
       
    public void createTeam(){

        try {
            courseCode=getCourseCode();
            Team team = new Team();
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Student user = (Student)session.getAttribute("User");
            team.setLiaisonId(user.getUserId());

            TeamParameters params = teamFacade.findTeamParams(courseCode);


            if(params == null) {
                status="Invalid Course, please try again";
             } else {
            
                team.setDateOfCreation();
                team.setTeamName(teamName);
                
                
                membersString = membersString.replaceAll("\\s", ""); 
                boolean flag = true;
                String[] tmp_members = membersString.split(",");
                for (int i=0; i<tmp_members.length; i++){
                    Student student = (Student)userFacade.findStudent(tmp_members[i]);
                    if (student == null){
                        throw new Exception();
                        
                    }
                    else if(student.getTeamId()!=null){
                        flag=false;
                        status="One of the students is already in a team";
                    }
                }
                if (flag){
                    for (int i=0; i<tmp_members.length; i++){

                        Student student = (Student)userFacade.findStudent(tmp_members[i]);
                        
                        if(i>params.getMaxStudents()-1){
                            status="Number of team members exceeds limit";
                        }

                        else{
                            student.setTeamId(getCourseCode()+user.getUserId());
                            userFacade.editStudent(student);
                        }
                    }
                    user.setTeamId( getCourseCode()+user.getUserId());
                    userFacade.editStudent(user);


                    team.setTeamId(getCourseCode()+user.getUserId());

                    team.setCourseCode(courseCode);

                    if(tmp_members.length+1<params.getMinStudents()){
                        team.setTeamStatus("incomplete");
                    }
                    else if(tmp_members.length+1<params.getMaxStudents()){
                        team.setTeamStatus("valid");
                    }
                    else{
                        team.setTeamStatus("complete");
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

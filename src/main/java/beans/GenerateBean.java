/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import persistence.Course;
import persistence.CourseSection;

/**
 *
 * @author aman
 */
@Named(value = "generateBean")
@RequestScoped
public class GenerateBean {
    @EJB
    private ObjectFacadeLocal objectFacade;
    private boolean isGenerated;
    /**
     * Creates a new instance of JoinTeamBean
     */
    public GenerateBean() {
    }
    
    
    public void addCourseAndSection(){
        try{
            Course course = new Course();
            course.setCourseCode("SEG3502");
            CourseSection section = new CourseSection();
            section.setCourseCode("SEG3502");
            section.setSectionCode("A01");
            objectFacade.addCourseAndSection(course, section); 
        }catch(Exception e){
           System.out.println(e.getMessage());
        }  
        
    }
    
    
    public String isGenerated(){
        try{
            
           if(objectFacade.countCourses() == 0){
               isGenerated = true;
           }
           else{
               isGenerated = false;
           }
            
                
        
        }catch(Exception e){
           System.out.println(e.getMessage());
        }  
        return "generate";
    }
    
    
    
    public void setIsGenerated(boolean isGenerated){
        this.isGenerated = isGenerated;
    }
    
    public boolean getIsGenerated(){
        return isGenerated;
    }
    
    
    
    
   
    

}

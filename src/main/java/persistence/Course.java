/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author gmonz36
 */
@Entity
@Table(name="COURSE_TABLE8930730")
public class Course implements Serializable{
    
    @Id
    String courseCode;
    String InstructorId;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstructorId() {
        return InstructorId;
    }

    public void setInstructorId(String InstructorId) {
        this.InstructorId = InstructorId;
    }
    
    
}

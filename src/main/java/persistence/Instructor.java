package persistence;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gmonz36
 */
@Entity
@Table(name="INSTRUCTOR_TABLE8930730")
public class Instructor extends User  {
    private String courseCode;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
      
    }
}

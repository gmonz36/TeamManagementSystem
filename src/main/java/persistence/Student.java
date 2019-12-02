package persistence;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gmonz36
 */
@Entity
@Table(name="STUDENT_TABLE8930730")
public class Student extends User {
    private String program;
    private String teamId;
    private String sectionCode;
    /**
     * @return the program
     */
    public String getProgram() {
        return program;
    }

    /**
     * @param program the program to set
     */
    public void setProgram(String firstname) {
        this.program = program;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    
    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

  
    
}



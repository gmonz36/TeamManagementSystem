package persistence;

import javax.persistence.Entity;

/**
 *
 * @author gmonz36
 */
@Entity
public class Student extends User {
    private String program;
    
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author gmonz36
 */
@Entity
@Table(name="REQUEST_TABLE8930730")
public class Request implements Serializable{
    
    @Id @GeneratedValue long id;
    String status;
    String studentId;
    String teamId;

    public Request() {
    }

    public Request(long id, String status, String studentId, String teamId) {
        this.id = id;
        this.status = status;
        this.studentId = studentId;
        this.teamId = teamId;
    }
    
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

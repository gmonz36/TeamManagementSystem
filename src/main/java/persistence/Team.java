/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;


/**
 *
 * @author gmonz36
 */
@Entity
@Table(name="TEAM_TABLE8930730")

public class Team implements Serializable{
    
    @Id
    String courseCode;
    String teamId;
    String teamName;
    LocalDate dateOfCreation;
    boolean teamStatus;
    String liaisonId;
    String membersString;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Student user = (Student)session.getAttribute("User");
        this.teamId = getCourseCode()+user.getUserId();
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
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    } 
    
    public String getMembersString() {
        return membersString;
    }

    public void setMembersString(String membersString) {
        this.membersString = membersString;
    } 
}

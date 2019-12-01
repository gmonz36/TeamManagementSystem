/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author gmonz36
 */
@Entity
@Table(name="TEAM_TABLE8930730")
public class Team implements Serializable{
    
    @Id
    String courseCode;
    @Id
    String teamId;
    String teamName;
    LocalDate dateOfCreation;
    boolean teamStatus;
    String liaisonId;
    Student[] members;

    public Team(String courseCode, String teamId, LocalDate dateOfCreation, boolean teamStatus, String liaisonId) {
        this.courseCode = courseCode;
        this.teamId = teamId;
        this.dateOfCreation = dateOfCreation;
        this.teamStatus = teamStatus;
        this.liaisonId = liaisonId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public boolean isTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(boolean teamStatus) {
        this.teamStatus = teamStatus;
    }

    public String getLiaisonId() {
        return liaisonId;
    }

    public void setLiaisonId(String liaisonId) {
        this.liaisonId = liaisonId;
    } 
}
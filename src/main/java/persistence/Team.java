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
    String teamId;
    String teamName;
    LocalDate dateOfCreation;
    boolean teamStatus;
    String liaisonId;
    Student[] members;

    public String get_teamId() {
        return teamId;
    }

    public void set_teamId(String teamId) {
        this.teamId = teamId;
    }

    public String get_teamName() {
        return teamName;
    }

    public void set_teamName(String teamName) {
        this.teamName = teamName;
    }

    public LocalDate get_dateOfCreation() {
        return dateOfCreation;
    }

    public void set_dateOfCreation() {
        this.dateOfCreation = java.time.LocalDate.now();
    }

    public boolean get_teamStatus () {
        return teamStatus;
    }

    public void set_teamStatus(boolean teamStatus) {
        this.teamStatus = teamStatus;
    }

    public String get_liaisonId() {
        return liaisonId;
    }

    public void set_liaisonId(String liaisonId) {
        this.liaisonId = liaisonId;
    }
    
    public Student[] get_members() {
        return members;
    }

    public void set_members(Student[] members) {
        this.members = members;
    }

    public String get_CourseCode() {
        return courseCode;
    }

    public void set_CourseCode(String courseCode) {
        this.courseCode = courseCode;
    } 
    
}

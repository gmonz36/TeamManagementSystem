/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author gmonz36
 */
@Entity
@Table(name="TEAM_TABLE8930730")
public class Team extends TeamParameters {
    
    String teamId;
    String teamName;
    String dateOfCreation;
    boolean status;
    String liaisonId;
    
    
}

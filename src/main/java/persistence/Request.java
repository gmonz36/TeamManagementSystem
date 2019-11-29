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
    boolean status;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import persistence.Team;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ssome
 */
@Local
public interface TeamFacadeLocal {

    void create(Team user);

    void edit(Team user);

    void remove(Team user);

    Team find(Object id);

    List<Team> findAll();

    List<Team> findRange(int[] range);

    int count();
    
    
    public List<Team> findById(String id);
    
}

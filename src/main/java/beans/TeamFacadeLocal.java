/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import persistence.Team;
import java.util.List;
import javax.ejb.Local;
import persistence.TeamParameters;

/**
 *
 * @author ssome
 */
@Local
public interface TeamFacadeLocal {

    void addTeam(Team team);

    void edit(Team team);

    void remove(Team team);

    Team find(Object id);

    List<Team> findAll();

    List<Team> findRange(int[] range);

    int count();
    
    TeamParameters findTeamParams(Object courseCode);

    
    public List<Team> findById(String id);
    
}

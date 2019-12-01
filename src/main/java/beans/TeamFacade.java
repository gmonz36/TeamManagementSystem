/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import persistence.Team;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import persistence.Student;
import persistence.TeamParameters;

/**
 *
 * @author ssome
 */
@Stateless
public class TeamFacade extends AbstractFacade<Team> implements TeamFacadeLocal {
    @PersistenceContext(unitName = "TeamManagementSystemPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamFacade() {
        super(Team.class);
    }


    
    public void addTeam(Team team) {
        getEntityManager().persist(team);
        

    }
    
    
    @Override
    public TeamParameters findTeamParams(Object courseCode) {
        return getEntityManager().find(TeamParameters.class, courseCode);

    }

    
    @Override
    public List<Team> findById(String id) {
//        try {
//            Query query = em.createQuery(
//                "SELECT u FROM Item u" +
//                " WHERE u.category = :ItemCategory");
//            query.setParameter("ItemCategory",category);
//
//            List resultList = query.getResultList();
//            return resultList;
//        } catch (Exception e) {
//        }
        return null;
    }    
}

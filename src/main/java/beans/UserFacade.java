/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import persistence.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ssome
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {
    @PersistenceContext(unitName = "TeamManagementSystemPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    

    @Override
    public List<User> findById(String id) {
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

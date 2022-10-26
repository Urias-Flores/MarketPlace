/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.Buy;
import Models.Buydetail;
import Models.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class BuydetailJpaController implements Serializable {

    public BuydetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Buydetail buydetail) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buy buyID = buydetail.getBuyID();
            if (buyID != null) {
                buyID = em.getReference(buyID.getClass(), buyID.getBuyID());
                buydetail.setBuyID(buyID);
            }
            Product productID = buydetail.getProductID();
            if (productID != null) {
                productID = em.getReference(productID.getClass(), productID.getProducrID());
                buydetail.setProductID(productID);
            }
            em.persist(buydetail);
            if (buyID != null) {
                buyID.getBuydetailList().add(buydetail);
                buyID = em.merge(buyID);
            }
            if (productID != null) {
                productID.getBuydetailList().add(buydetail);
                productID = em.merge(productID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Buydetail buydetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buydetail persistentBuydetail = em.find(Buydetail.class, buydetail.getBuyDetailID());
            Buy buyIDOld = persistentBuydetail.getBuyID();
            Buy buyIDNew = buydetail.getBuyID();
            Product productIDOld = persistentBuydetail.getProductID();
            Product productIDNew = buydetail.getProductID();
            if (buyIDNew != null) {
                buyIDNew = em.getReference(buyIDNew.getClass(), buyIDNew.getBuyID());
                buydetail.setBuyID(buyIDNew);
            }
            if (productIDNew != null) {
                productIDNew = em.getReference(productIDNew.getClass(), productIDNew.getProducrID());
                buydetail.setProductID(productIDNew);
            }
            buydetail = em.merge(buydetail);
            if (buyIDOld != null && !buyIDOld.equals(buyIDNew)) {
                buyIDOld.getBuydetailList().remove(buydetail);
                buyIDOld = em.merge(buyIDOld);
            }
            if (buyIDNew != null && !buyIDNew.equals(buyIDOld)) {
                buyIDNew.getBuydetailList().add(buydetail);
                buyIDNew = em.merge(buyIDNew);
            }
            if (productIDOld != null && !productIDOld.equals(productIDNew)) {
                productIDOld.getBuydetailList().remove(buydetail);
                productIDOld = em.merge(productIDOld);
            }
            if (productIDNew != null && !productIDNew.equals(productIDOld)) {
                productIDNew.getBuydetailList().add(buydetail);
                productIDNew = em.merge(productIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = buydetail.getBuyDetailID();
                if (findBuydetail(id) == null) {
                    throw new NonexistentEntityException("The buydetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buydetail buydetail;
            try {
                buydetail = em.getReference(Buydetail.class, id);
                buydetail.getBuyDetailID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buydetail with id " + id + " no longer exists.", enfe);
            }
            Buy buyID = buydetail.getBuyID();
            if (buyID != null) {
                buyID.getBuydetailList().remove(buydetail);
                buyID = em.merge(buyID);
            }
            Product productID = buydetail.getProductID();
            if (productID != null) {
                productID.getBuydetailList().remove(buydetail);
                productID = em.merge(productID);
            }
            em.remove(buydetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Buydetail> findBuydetailEntities() {
        return findBuydetailEntities(true, -1, -1);
    }

    public List<Buydetail> findBuydetailEntities(int maxResults, int firstResult) {
        return findBuydetailEntities(false, maxResults, firstResult);
    }

    private List<Buydetail> findBuydetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Buydetail.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Buydetail findBuydetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Buydetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getBuydetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Buydetail> rt = cq.from(Buydetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

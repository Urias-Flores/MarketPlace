/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Models.Buy;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.Supplier;
import Models.Buydetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class BuyJpaController implements Serializable {

    public BuyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Buy buy) {
        if (buy.getBuydetailList() == null) {
            buy.setBuydetailList(new ArrayList<Buydetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Supplier supplier = buy.getSupplier();
            if (supplier != null) {
                supplier = em.getReference(supplier.getClass(), supplier.getSupplierID());
                buy.setSupplier(supplier);
            }
            List<Buydetail> attachedBuydetailList = new ArrayList<Buydetail>();
            for (Buydetail buydetailListBuydetailToAttach : buy.getBuydetailList()) {
                buydetailListBuydetailToAttach = em.getReference(buydetailListBuydetailToAttach.getClass(), buydetailListBuydetailToAttach.getBuyDetailID());
                attachedBuydetailList.add(buydetailListBuydetailToAttach);
            }
            buy.setBuydetailList(attachedBuydetailList);
            em.persist(buy);
            if (supplier != null) {
                supplier.getBuyList().add(buy);
                supplier = em.merge(supplier);
            }
            for (Buydetail buydetailListBuydetail : buy.getBuydetailList()) {
                Buy oldBuyIDOfBuydetailListBuydetail = buydetailListBuydetail.getBuyID();
                buydetailListBuydetail.setBuyID(buy);
                buydetailListBuydetail = em.merge(buydetailListBuydetail);
                if (oldBuyIDOfBuydetailListBuydetail != null) {
                    oldBuyIDOfBuydetailListBuydetail.getBuydetailList().remove(buydetailListBuydetail);
                    oldBuyIDOfBuydetailListBuydetail = em.merge(oldBuyIDOfBuydetailListBuydetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Buy buy) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buy persistentBuy = em.find(Buy.class, buy.getBuyID());
            Supplier supplierOld = persistentBuy.getSupplier();
            Supplier supplierNew = buy.getSupplier();
            List<Buydetail> buydetailListOld = persistentBuy.getBuydetailList();
            List<Buydetail> buydetailListNew = buy.getBuydetailList();
            List<String> illegalOrphanMessages = null;
            for (Buydetail buydetailListOldBuydetail : buydetailListOld) {
                if (!buydetailListNew.contains(buydetailListOldBuydetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Buydetail " + buydetailListOldBuydetail + " since its buyID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (supplierNew != null) {
                supplierNew = em.getReference(supplierNew.getClass(), supplierNew.getSupplierID());
                buy.setSupplier(supplierNew);
            }
            List<Buydetail> attachedBuydetailListNew = new ArrayList<Buydetail>();
            for (Buydetail buydetailListNewBuydetailToAttach : buydetailListNew) {
                buydetailListNewBuydetailToAttach = em.getReference(buydetailListNewBuydetailToAttach.getClass(), buydetailListNewBuydetailToAttach.getBuyDetailID());
                attachedBuydetailListNew.add(buydetailListNewBuydetailToAttach);
            }
            buydetailListNew = attachedBuydetailListNew;
            buy.setBuydetailList(buydetailListNew);
            buy = em.merge(buy);
            if (supplierOld != null && !supplierOld.equals(supplierNew)) {
                supplierOld.getBuyList().remove(buy);
                supplierOld = em.merge(supplierOld);
            }
            if (supplierNew != null && !supplierNew.equals(supplierOld)) {
                supplierNew.getBuyList().add(buy);
                supplierNew = em.merge(supplierNew);
            }
            for (Buydetail buydetailListNewBuydetail : buydetailListNew) {
                if (!buydetailListOld.contains(buydetailListNewBuydetail)) {
                    Buy oldBuyIDOfBuydetailListNewBuydetail = buydetailListNewBuydetail.getBuyID();
                    buydetailListNewBuydetail.setBuyID(buy);
                    buydetailListNewBuydetail = em.merge(buydetailListNewBuydetail);
                    if (oldBuyIDOfBuydetailListNewBuydetail != null && !oldBuyIDOfBuydetailListNewBuydetail.equals(buy)) {
                        oldBuyIDOfBuydetailListNewBuydetail.getBuydetailList().remove(buydetailListNewBuydetail);
                        oldBuyIDOfBuydetailListNewBuydetail = em.merge(oldBuyIDOfBuydetailListNewBuydetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = buy.getBuyID();
                if (findBuy(id) == null) {
                    throw new NonexistentEntityException("The buy with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Buy buy;
            try {
                buy = em.getReference(Buy.class, id);
                buy.getBuyID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The buy with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Buydetail> buydetailListOrphanCheck = buy.getBuydetailList();
            for (Buydetail buydetailListOrphanCheckBuydetail : buydetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Buy (" + buy + ") cannot be destroyed since the Buydetail " + buydetailListOrphanCheckBuydetail + " in its buydetailList field has a non-nullable buyID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Supplier supplier = buy.getSupplier();
            if (supplier != null) {
                supplier.getBuyList().remove(buy);
                supplier = em.merge(supplier);
            }
            em.remove(buy);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Buy> findBuyEntities() {
        return findBuyEntities(true, -1, -1);
    }

    public List<Buy> findBuyEntities(int maxResults, int firstResult) {
        return findBuyEntities(false, maxResults, firstResult);
    }

    private List<Buy> findBuyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Buy.class));
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

    public Buy findBuy(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Buy.class, id);
        } finally {
            em.close();
        }
    }

    public int getBuyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Buy> rt = cq.from(Buy.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

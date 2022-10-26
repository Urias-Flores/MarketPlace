/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.Location;
import Models.Buy;
import Models.Supplier;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class SupplierJpaController implements Serializable {

    public SupplierJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Supplier supplier) {
        if (supplier.getBuyList() == null) {
            supplier.setBuyList(new ArrayList<Buy>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Location location = supplier.getLocation();
            if (location != null) {
                location = em.getReference(location.getClass(), location.getLocationID());
                supplier.setLocation(location);
            }
            List<Buy> attachedBuyList = new ArrayList<Buy>();
            for (Buy buyListBuyToAttach : supplier.getBuyList()) {
                buyListBuyToAttach = em.getReference(buyListBuyToAttach.getClass(), buyListBuyToAttach.getBuyID());
                attachedBuyList.add(buyListBuyToAttach);
            }
            supplier.setBuyList(attachedBuyList);
            em.persist(supplier);
            if (location != null) {
                location.getSupplierList().add(supplier);
                location = em.merge(location);
            }
            for (Buy buyListBuy : supplier.getBuyList()) {
                Supplier oldSupplierOfBuyListBuy = buyListBuy.getSupplier();
                buyListBuy.setSupplier(supplier);
                buyListBuy = em.merge(buyListBuy);
                if (oldSupplierOfBuyListBuy != null) {
                    oldSupplierOfBuyListBuy.getBuyList().remove(buyListBuy);
                    oldSupplierOfBuyListBuy = em.merge(oldSupplierOfBuyListBuy);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Supplier supplier) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Supplier persistentSupplier = em.find(Supplier.class, supplier.getSupplierID());
            Location locationOld = persistentSupplier.getLocation();
            Location locationNew = supplier.getLocation();
            List<Buy> buyListOld = persistentSupplier.getBuyList();
            List<Buy> buyListNew = supplier.getBuyList();
            List<String> illegalOrphanMessages = null;
            for (Buy buyListOldBuy : buyListOld) {
                if (!buyListNew.contains(buyListOldBuy)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Buy " + buyListOldBuy + " since its supplier field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (locationNew != null) {
                locationNew = em.getReference(locationNew.getClass(), locationNew.getLocationID());
                supplier.setLocation(locationNew);
            }
            List<Buy> attachedBuyListNew = new ArrayList<Buy>();
            for (Buy buyListNewBuyToAttach : buyListNew) {
                buyListNewBuyToAttach = em.getReference(buyListNewBuyToAttach.getClass(), buyListNewBuyToAttach.getBuyID());
                attachedBuyListNew.add(buyListNewBuyToAttach);
            }
            buyListNew = attachedBuyListNew;
            supplier.setBuyList(buyListNew);
            supplier = em.merge(supplier);
            if (locationOld != null && !locationOld.equals(locationNew)) {
                locationOld.getSupplierList().remove(supplier);
                locationOld = em.merge(locationOld);
            }
            if (locationNew != null && !locationNew.equals(locationOld)) {
                locationNew.getSupplierList().add(supplier);
                locationNew = em.merge(locationNew);
            }
            for (Buy buyListNewBuy : buyListNew) {
                if (!buyListOld.contains(buyListNewBuy)) {
                    Supplier oldSupplierOfBuyListNewBuy = buyListNewBuy.getSupplier();
                    buyListNewBuy.setSupplier(supplier);
                    buyListNewBuy = em.merge(buyListNewBuy);
                    if (oldSupplierOfBuyListNewBuy != null && !oldSupplierOfBuyListNewBuy.equals(supplier)) {
                        oldSupplierOfBuyListNewBuy.getBuyList().remove(buyListNewBuy);
                        oldSupplierOfBuyListNewBuy = em.merge(oldSupplierOfBuyListNewBuy);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = supplier.getSupplierID();
                if (findSupplier(id) == null) {
                    throw new NonexistentEntityException("The supplier with id " + id + " no longer exists.");
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
            Supplier supplier;
            try {
                supplier = em.getReference(Supplier.class, id);
                supplier.getSupplierID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The supplier with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Buy> buyListOrphanCheck = supplier.getBuyList();
            for (Buy buyListOrphanCheckBuy : buyListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Supplier (" + supplier + ") cannot be destroyed since the Buy " + buyListOrphanCheckBuy + " in its buyList field has a non-nullable supplier field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Location location = supplier.getLocation();
            if (location != null) {
                location.getSupplierList().remove(supplier);
                location = em.merge(location);
            }
            em.remove(supplier);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Supplier> findSupplierEntities() {
        return findSupplierEntities(true, -1, -1);
    }

    public List<Supplier> findSupplierEntities(int maxResults, int firstResult) {
        return findSupplierEntities(false, maxResults, firstResult);
    }

    private List<Supplier> findSupplierEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Supplier.class));
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

    public Supplier findSupplier(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Supplier.class, id);
        } finally {
            em.close();
        }
    }

    public int getSupplierCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Supplier> rt = cq.from(Supplier.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

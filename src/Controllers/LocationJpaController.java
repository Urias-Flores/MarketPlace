/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Models.Location;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.Supplier;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class LocationJpaController implements Serializable {

    public LocationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Location location) {
        if (location.getSupplierList() == null) {
            location.setSupplierList(new ArrayList<Supplier>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Supplier> attachedSupplierList = new ArrayList<Supplier>();
            for (Supplier supplierListSupplierToAttach : location.getSupplierList()) {
                supplierListSupplierToAttach = em.getReference(supplierListSupplierToAttach.getClass(), supplierListSupplierToAttach.getSupplierID());
                attachedSupplierList.add(supplierListSupplierToAttach);
            }
            location.setSupplierList(attachedSupplierList);
            em.persist(location);
            for (Supplier supplierListSupplier : location.getSupplierList()) {
                Location oldLocationOfSupplierListSupplier = supplierListSupplier.getLocation();
                supplierListSupplier.setLocation(location);
                supplierListSupplier = em.merge(supplierListSupplier);
                if (oldLocationOfSupplierListSupplier != null) {
                    oldLocationOfSupplierListSupplier.getSupplierList().remove(supplierListSupplier);
                    oldLocationOfSupplierListSupplier = em.merge(oldLocationOfSupplierListSupplier);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Location location) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Location persistentLocation = em.find(Location.class, location.getLocationID());
            List<Supplier> supplierListOld = persistentLocation.getSupplierList();
            List<Supplier> supplierListNew = location.getSupplierList();
            List<String> illegalOrphanMessages = null;
            for (Supplier supplierListOldSupplier : supplierListOld) {
                if (!supplierListNew.contains(supplierListOldSupplier)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Supplier " + supplierListOldSupplier + " since its location field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Supplier> attachedSupplierListNew = new ArrayList<Supplier>();
            for (Supplier supplierListNewSupplierToAttach : supplierListNew) {
                supplierListNewSupplierToAttach = em.getReference(supplierListNewSupplierToAttach.getClass(), supplierListNewSupplierToAttach.getSupplierID());
                attachedSupplierListNew.add(supplierListNewSupplierToAttach);
            }
            supplierListNew = attachedSupplierListNew;
            location.setSupplierList(supplierListNew);
            location = em.merge(location);
            for (Supplier supplierListNewSupplier : supplierListNew) {
                if (!supplierListOld.contains(supplierListNewSupplier)) {
                    Location oldLocationOfSupplierListNewSupplier = supplierListNewSupplier.getLocation();
                    supplierListNewSupplier.setLocation(location);
                    supplierListNewSupplier = em.merge(supplierListNewSupplier);
                    if (oldLocationOfSupplierListNewSupplier != null && !oldLocationOfSupplierListNewSupplier.equals(location)) {
                        oldLocationOfSupplierListNewSupplier.getSupplierList().remove(supplierListNewSupplier);
                        oldLocationOfSupplierListNewSupplier = em.merge(oldLocationOfSupplierListNewSupplier);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = location.getLocationID();
                if (findLocation(id) == null) {
                    throw new NonexistentEntityException("The location with id " + id + " no longer exists.");
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
            Location location;
            try {
                location = em.getReference(Location.class, id);
                location.getLocationID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The location with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Supplier> supplierListOrphanCheck = location.getSupplierList();
            for (Supplier supplierListOrphanCheckSupplier : supplierListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Location (" + location + ") cannot be destroyed since the Supplier " + supplierListOrphanCheckSupplier + " in its supplierList field has a non-nullable location field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(location);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Location> findLocationEntities() {
        return findLocationEntities(true, -1, -1);
    }

    public List<Location> findLocationEntities(int maxResults, int firstResult) {
        return findLocationEntities(false, maxResults, firstResult);
    }

    private List<Location> findLocationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Location.class));
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

    public Location findLocation(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Location.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Location> rt = cq.from(Location.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import Models.Invoice;
import Models.Invoicedetail;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class InvoicedetailJpaController implements Serializable {

    public InvoicedetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Invoicedetail invoicedetail) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Invoice invoiceID = invoicedetail.getInvoiceID();
            if (invoiceID != null) {
                invoiceID = em.getReference(invoiceID.getClass(), invoiceID.getInvoiveID());
                invoicedetail.setInvoiceID(invoiceID);
            }
            em.persist(invoicedetail);
            if (invoiceID != null) {
                invoiceID.getInvoicedetailList().add(invoicedetail);
                invoiceID = em.merge(invoiceID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Invoicedetail invoicedetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Invoicedetail persistentInvoicedetail = em.find(Invoicedetail.class, invoicedetail.getInvoiceDetailID());
            Invoice invoiceIDOld = persistentInvoicedetail.getInvoiceID();
            Invoice invoiceIDNew = invoicedetail.getInvoiceID();
            if (invoiceIDNew != null) {
                invoiceIDNew = em.getReference(invoiceIDNew.getClass(), invoiceIDNew.getInvoiveID());
                invoicedetail.setInvoiceID(invoiceIDNew);
            }
            invoicedetail = em.merge(invoicedetail);
            if (invoiceIDOld != null && !invoiceIDOld.equals(invoiceIDNew)) {
                invoiceIDOld.getInvoicedetailList().remove(invoicedetail);
                invoiceIDOld = em.merge(invoiceIDOld);
            }
            if (invoiceIDNew != null && !invoiceIDNew.equals(invoiceIDOld)) {
                invoiceIDNew.getInvoicedetailList().add(invoicedetail);
                invoiceIDNew = em.merge(invoiceIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = invoicedetail.getInvoiceDetailID();
                if (findInvoicedetail(id) == null) {
                    throw new NonexistentEntityException("The invoicedetail with id " + id + " no longer exists.");
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
            Invoicedetail invoicedetail;
            try {
                invoicedetail = em.getReference(Invoicedetail.class, id);
                invoicedetail.getInvoiceDetailID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoicedetail with id " + id + " no longer exists.", enfe);
            }
            Invoice invoiceID = invoicedetail.getInvoiceID();
            if (invoiceID != null) {
                invoiceID.getInvoicedetailList().remove(invoicedetail);
                invoiceID = em.merge(invoiceID);
            }
            em.remove(invoicedetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Invoicedetail> findInvoicedetailEntities() {
        return findInvoicedetailEntities(true, -1, -1);
    }

    public List<Invoicedetail> findInvoicedetailEntities(int maxResults, int firstResult) {
        return findInvoicedetailEntities(false, maxResults, firstResult);
    }

    private List<Invoicedetail> findInvoicedetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Invoicedetail.class));
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

    public Invoicedetail findInvoicedetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Invoicedetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvoicedetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Invoicedetail> rt = cq.from(Invoicedetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import Models.Clients;
import Models.Invoice;
import Models.Invoicedetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class InvoiceJpaController implements Serializable {

    public InvoiceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Invoice invoice) {
        if (invoice.getInvoicedetailList() == null) {
            invoice.setInvoicedetailList(new ArrayList<Invoicedetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clients clients = invoice.getClients();
            if (clients != null) {
                clients = em.getReference(clients.getClass(), clients.getClientID());
                invoice.setClients(clients);
            }
            List<Invoicedetail> attachedInvoicedetailList = new ArrayList<Invoicedetail>();
            for (Invoicedetail invoicedetailListInvoicedetailToAttach : invoice.getInvoicedetailList()) {
                invoicedetailListInvoicedetailToAttach = em.getReference(invoicedetailListInvoicedetailToAttach.getClass(), invoicedetailListInvoicedetailToAttach.getInvoiceDetailID());
                attachedInvoicedetailList.add(invoicedetailListInvoicedetailToAttach);
            }
            invoice.setInvoicedetailList(attachedInvoicedetailList);
            em.persist(invoice);
            if (clients != null) {
                clients.getInvoiceList().add(invoice);
                clients = em.merge(clients);
            }
            for (Invoicedetail invoicedetailListInvoicedetail : invoice.getInvoicedetailList()) {
                Invoice oldInvoiceIDOfInvoicedetailListInvoicedetail = invoicedetailListInvoicedetail.getInvoiceID();
                invoicedetailListInvoicedetail.setInvoiceID(invoice);
                invoicedetailListInvoicedetail = em.merge(invoicedetailListInvoicedetail);
                if (oldInvoiceIDOfInvoicedetailListInvoicedetail != null) {
                    oldInvoiceIDOfInvoicedetailListInvoicedetail.getInvoicedetailList().remove(invoicedetailListInvoicedetail);
                    oldInvoiceIDOfInvoicedetailListInvoicedetail = em.merge(oldInvoiceIDOfInvoicedetailListInvoicedetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Invoice invoice) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Invoice persistentInvoice = em.find(Invoice.class, invoice.getInvoiveID());
            Clients clientsOld = persistentInvoice.getClients();
            Clients clientsNew = invoice.getClients();
            List<Invoicedetail> invoicedetailListOld = persistentInvoice.getInvoicedetailList();
            List<Invoicedetail> invoicedetailListNew = invoice.getInvoicedetailList();
            List<String> illegalOrphanMessages = null;
            for (Invoicedetail invoicedetailListOldInvoicedetail : invoicedetailListOld) {
                if (!invoicedetailListNew.contains(invoicedetailListOldInvoicedetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Invoicedetail " + invoicedetailListOldInvoicedetail + " since its invoiceID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientsNew != null) {
                clientsNew = em.getReference(clientsNew.getClass(), clientsNew.getClientID());
                invoice.setClients(clientsNew);
            }
            List<Invoicedetail> attachedInvoicedetailListNew = new ArrayList<Invoicedetail>();
            for (Invoicedetail invoicedetailListNewInvoicedetailToAttach : invoicedetailListNew) {
                invoicedetailListNewInvoicedetailToAttach = em.getReference(invoicedetailListNewInvoicedetailToAttach.getClass(), invoicedetailListNewInvoicedetailToAttach.getInvoiceDetailID());
                attachedInvoicedetailListNew.add(invoicedetailListNewInvoicedetailToAttach);
            }
            invoicedetailListNew = attachedInvoicedetailListNew;
            invoice.setInvoicedetailList(invoicedetailListNew);
            invoice = em.merge(invoice);
            if (clientsOld != null && !clientsOld.equals(clientsNew)) {
                clientsOld.getInvoiceList().remove(invoice);
                clientsOld = em.merge(clientsOld);
            }
            if (clientsNew != null && !clientsNew.equals(clientsOld)) {
                clientsNew.getInvoiceList().add(invoice);
                clientsNew = em.merge(clientsNew);
            }
            for (Invoicedetail invoicedetailListNewInvoicedetail : invoicedetailListNew) {
                if (!invoicedetailListOld.contains(invoicedetailListNewInvoicedetail)) {
                    Invoice oldInvoiceIDOfInvoicedetailListNewInvoicedetail = invoicedetailListNewInvoicedetail.getInvoiceID();
                    invoicedetailListNewInvoicedetail.setInvoiceID(invoice);
                    invoicedetailListNewInvoicedetail = em.merge(invoicedetailListNewInvoicedetail);
                    if (oldInvoiceIDOfInvoicedetailListNewInvoicedetail != null && !oldInvoiceIDOfInvoicedetailListNewInvoicedetail.equals(invoice)) {
                        oldInvoiceIDOfInvoicedetailListNewInvoicedetail.getInvoicedetailList().remove(invoicedetailListNewInvoicedetail);
                        oldInvoiceIDOfInvoicedetailListNewInvoicedetail = em.merge(oldInvoiceIDOfInvoicedetailListNewInvoicedetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = invoice.getInvoiveID();
                if (findInvoice(id) == null) {
                    throw new NonexistentEntityException("The invoice with id " + id + " no longer exists.");
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
            Invoice invoice;
            try {
                invoice = em.getReference(Invoice.class, id);
                invoice.getInvoiveID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoice with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Invoicedetail> invoicedetailListOrphanCheck = invoice.getInvoicedetailList();
            for (Invoicedetail invoicedetailListOrphanCheckInvoicedetail : invoicedetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Invoice (" + invoice + ") cannot be destroyed since the Invoicedetail " + invoicedetailListOrphanCheckInvoicedetail + " in its invoicedetailList field has a non-nullable invoiceID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Clients clients = invoice.getClients();
            if (clients != null) {
                clients.getInvoiceList().remove(invoice);
                clients = em.merge(clients);
            }
            em.remove(invoice);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Invoice> findInvoiceEntities() {
        return findInvoiceEntities(true, -1, -1);
    }

    public List<Invoice> findInvoiceEntities(int maxResults, int firstResult) {
        return findInvoiceEntities(false, maxResults, firstResult);
    }

    private List<Invoice> findInvoiceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Invoice.class));
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

    public Invoice findInvoice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Invoice.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvoiceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Invoice> rt = cq.from(Invoice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

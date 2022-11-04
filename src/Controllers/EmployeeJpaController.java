/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Models.Employee;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) {
        if (employee.getUsersCollection() == null) {
            employee.setUsersCollection(new ArrayList<Users>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : employee.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getUserID());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            employee.setUsersCollection(attachedUsersCollection);
            em.persist(employee);
            for (Users usersCollectionUsers : employee.getUsersCollection()) {
                Employee oldEmployeeOfUsersCollectionUsers = usersCollectionUsers.getEmployee();
                usersCollectionUsers.setEmployee(employee);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldEmployeeOfUsersCollectionUsers != null) {
                    oldEmployeeOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldEmployeeOfUsersCollectionUsers = em.merge(oldEmployeeOfUsersCollectionUsers);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee persistentEmployee = em.find(Employee.class, employee.getEmpleyeeID());
            Collection<Users> usersCollectionOld = persistentEmployee.getUsersCollection();
            Collection<Users> usersCollectionNew = employee.getUsersCollection();
            List<String> illegalOrphanMessages = null;
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersCollectionOldUsers + " since its employee field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getUserID());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            employee.setUsersCollection(usersCollectionNew);
            employee = em.merge(employee);
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Employee oldEmployeeOfUsersCollectionNewUsers = usersCollectionNewUsers.getEmployee();
                    usersCollectionNewUsers.setEmployee(employee);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldEmployeeOfUsersCollectionNewUsers != null && !oldEmployeeOfUsersCollectionNewUsers.equals(employee)) {
                        oldEmployeeOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldEmployeeOfUsersCollectionNewUsers = em.merge(oldEmployeeOfUsersCollectionNewUsers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employee.getEmpleyeeID();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
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
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getEmpleyeeID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Users> usersCollectionOrphanCheck = employee.getUsersCollection();
            for (Users usersCollectionOrphanCheckUsers : usersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Users " + usersCollectionOrphanCheckUsers + " in its usersCollection field has a non-nullable employee field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
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

    public Employee findEmployee(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

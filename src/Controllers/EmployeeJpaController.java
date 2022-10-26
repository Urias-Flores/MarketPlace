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
import Models.Warehouse;
import java.util.ArrayList;
import java.util.List;
import Models.Users;
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
        if (employee.getWarehouseList() == null) {
            employee.setWarehouseList(new ArrayList<Warehouse>());
        }
        if (employee.getUsersList() == null) {
            employee.setUsersList(new ArrayList<Users>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Warehouse> attachedWarehouseList = new ArrayList<Warehouse>();
            for (Warehouse warehouseListWarehouseToAttach : employee.getWarehouseList()) {
                warehouseListWarehouseToAttach = em.getReference(warehouseListWarehouseToAttach.getClass(), warehouseListWarehouseToAttach.getWarehouseID());
                attachedWarehouseList.add(warehouseListWarehouseToAttach);
            }
            employee.setWarehouseList(attachedWarehouseList);
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : employee.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUserID());
                attachedUsersList.add(usersListUsersToAttach);
            }
            employee.setUsersList(attachedUsersList);
            em.persist(employee);
            for (Warehouse warehouseListWarehouse : employee.getWarehouseList()) {
                Employee oldEmployeeOfWarehouseListWarehouse = warehouseListWarehouse.getEmployee();
                warehouseListWarehouse.setEmployee(employee);
                warehouseListWarehouse = em.merge(warehouseListWarehouse);
                if (oldEmployeeOfWarehouseListWarehouse != null) {
                    oldEmployeeOfWarehouseListWarehouse.getWarehouseList().remove(warehouseListWarehouse);
                    oldEmployeeOfWarehouseListWarehouse = em.merge(oldEmployeeOfWarehouseListWarehouse);
                }
            }
            for (Users usersListUsers : employee.getUsersList()) {
                Employee oldEmployeeOfUsersListUsers = usersListUsers.getEmployee();
                usersListUsers.setEmployee(employee);
                usersListUsers = em.merge(usersListUsers);
                if (oldEmployeeOfUsersListUsers != null) {
                    oldEmployeeOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldEmployeeOfUsersListUsers = em.merge(oldEmployeeOfUsersListUsers);
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
            List<Warehouse> warehouseListOld = persistentEmployee.getWarehouseList();
            List<Warehouse> warehouseListNew = employee.getWarehouseList();
            List<Users> usersListOld = persistentEmployee.getUsersList();
            List<Users> usersListNew = employee.getUsersList();
            List<String> illegalOrphanMessages = null;
            for (Warehouse warehouseListOldWarehouse : warehouseListOld) {
                if (!warehouseListNew.contains(warehouseListOldWarehouse)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Warehouse " + warehouseListOldWarehouse + " since its employee field is not nullable.");
                }
            }
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersListOldUsers + " since its employee field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Warehouse> attachedWarehouseListNew = new ArrayList<Warehouse>();
            for (Warehouse warehouseListNewWarehouseToAttach : warehouseListNew) {
                warehouseListNewWarehouseToAttach = em.getReference(warehouseListNewWarehouseToAttach.getClass(), warehouseListNewWarehouseToAttach.getWarehouseID());
                attachedWarehouseListNew.add(warehouseListNewWarehouseToAttach);
            }
            warehouseListNew = attachedWarehouseListNew;
            employee.setWarehouseList(warehouseListNew);
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUserID());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            employee.setUsersList(usersListNew);
            employee = em.merge(employee);
            for (Warehouse warehouseListNewWarehouse : warehouseListNew) {
                if (!warehouseListOld.contains(warehouseListNewWarehouse)) {
                    Employee oldEmployeeOfWarehouseListNewWarehouse = warehouseListNewWarehouse.getEmployee();
                    warehouseListNewWarehouse.setEmployee(employee);
                    warehouseListNewWarehouse = em.merge(warehouseListNewWarehouse);
                    if (oldEmployeeOfWarehouseListNewWarehouse != null && !oldEmployeeOfWarehouseListNewWarehouse.equals(employee)) {
                        oldEmployeeOfWarehouseListNewWarehouse.getWarehouseList().remove(warehouseListNewWarehouse);
                        oldEmployeeOfWarehouseListNewWarehouse = em.merge(oldEmployeeOfWarehouseListNewWarehouse);
                    }
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Employee oldEmployeeOfUsersListNewUsers = usersListNewUsers.getEmployee();
                    usersListNewUsers.setEmployee(employee);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldEmployeeOfUsersListNewUsers != null && !oldEmployeeOfUsersListNewUsers.equals(employee)) {
                        oldEmployeeOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldEmployeeOfUsersListNewUsers = em.merge(oldEmployeeOfUsersListNewUsers);
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
            List<Warehouse> warehouseListOrphanCheck = employee.getWarehouseList();
            for (Warehouse warehouseListOrphanCheckWarehouse : warehouseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Warehouse " + warehouseListOrphanCheckWarehouse + " in its warehouseList field has a non-nullable employee field.");
            }
            List<Users> usersListOrphanCheck = employee.getUsersList();
            for (Users usersListOrphanCheckUsers : usersListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employee (" + employee + ") cannot be destroyed since the Users " + usersListOrphanCheckUsers + " in its usersList field has a non-nullable employee field.");
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

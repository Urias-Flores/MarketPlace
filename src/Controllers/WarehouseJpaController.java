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
import Models.Employee;
import Models.Product;
import Models.Warehouse;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class WarehouseJpaController implements Serializable {

    public WarehouseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Warehouse warehouse) {
        if (warehouse.getProductList() == null) {
            warehouse.setProductList(new ArrayList<Product>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee = warehouse.getEmployee();
            if (employee != null) {
                employee = em.getReference(employee.getClass(), employee.getEmpleyeeID());
                warehouse.setEmployee(employee);
            }
            List<Product> attachedProductList = new ArrayList<Product>();
            for (Product productListProductToAttach : warehouse.getProductList()) {
                productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getProducrID());
                attachedProductList.add(productListProductToAttach);
            }
            warehouse.setProductList(attachedProductList);
            em.persist(warehouse);
            if (employee != null) {
                employee.getWarehouseList().add(warehouse);
                employee = em.merge(employee);
            }
            for (Product productListProduct : warehouse.getProductList()) {
                Warehouse oldWarehouseOfProductListProduct = productListProduct.getWarehouse();
                productListProduct.setWarehouse(warehouse);
                productListProduct = em.merge(productListProduct);
                if (oldWarehouseOfProductListProduct != null) {
                    oldWarehouseOfProductListProduct.getProductList().remove(productListProduct);
                    oldWarehouseOfProductListProduct = em.merge(oldWarehouseOfProductListProduct);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Warehouse warehouse) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Warehouse persistentWarehouse = em.find(Warehouse.class, warehouse.getWarehouseID());
            Employee employeeOld = persistentWarehouse.getEmployee();
            Employee employeeNew = warehouse.getEmployee();
            List<Product> productListOld = persistentWarehouse.getProductList();
            List<Product> productListNew = warehouse.getProductList();
            if (employeeNew != null) {
                employeeNew = em.getReference(employeeNew.getClass(), employeeNew.getEmpleyeeID());
                warehouse.setEmployee(employeeNew);
            }
            List<Product> attachedProductListNew = new ArrayList<Product>();
            for (Product productListNewProductToAttach : productListNew) {
                productListNewProductToAttach = em.getReference(productListNewProductToAttach.getClass(), productListNewProductToAttach.getProducrID());
                attachedProductListNew.add(productListNewProductToAttach);
            }
            productListNew = attachedProductListNew;
            warehouse.setProductList(productListNew);
            warehouse = em.merge(warehouse);
            if (employeeOld != null && !employeeOld.equals(employeeNew)) {
                employeeOld.getWarehouseList().remove(warehouse);
                employeeOld = em.merge(employeeOld);
            }
            if (employeeNew != null && !employeeNew.equals(employeeOld)) {
                employeeNew.getWarehouseList().add(warehouse);
                employeeNew = em.merge(employeeNew);
            }
            for (Product productListOldProduct : productListOld) {
                if (!productListNew.contains(productListOldProduct)) {
                    productListOldProduct.setWarehouse(null);
                    productListOldProduct = em.merge(productListOldProduct);
                }
            }
            for (Product productListNewProduct : productListNew) {
                if (!productListOld.contains(productListNewProduct)) {
                    Warehouse oldWarehouseOfProductListNewProduct = productListNewProduct.getWarehouse();
                    productListNewProduct.setWarehouse(warehouse);
                    productListNewProduct = em.merge(productListNewProduct);
                    if (oldWarehouseOfProductListNewProduct != null && !oldWarehouseOfProductListNewProduct.equals(warehouse)) {
                        oldWarehouseOfProductListNewProduct.getProductList().remove(productListNewProduct);
                        oldWarehouseOfProductListNewProduct = em.merge(oldWarehouseOfProductListNewProduct);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = warehouse.getWarehouseID();
                if (findWarehouse(id) == null) {
                    throw new NonexistentEntityException("The warehouse with id " + id + " no longer exists.");
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
            Warehouse warehouse;
            try {
                warehouse = em.getReference(Warehouse.class, id);
                warehouse.getWarehouseID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The warehouse with id " + id + " no longer exists.", enfe);
            }
            Employee employee = warehouse.getEmployee();
            if (employee != null) {
                employee.getWarehouseList().remove(warehouse);
                employee = em.merge(employee);
            }
            List<Product> productList = warehouse.getProductList();
            for (Product productListProduct : productList) {
                productListProduct.setWarehouse(null);
                productListProduct = em.merge(productListProduct);
            }
            em.remove(warehouse);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Warehouse> findWarehouseEntities() {
        return findWarehouseEntities(true, -1, -1);
    }

    public List<Warehouse> findWarehouseEntities(int maxResults, int firstResult) {
        return findWarehouseEntities(false, maxResults, firstResult);
    }

    private List<Warehouse> findWarehouseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Warehouse.class));
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

    public Warehouse findWarehouse(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Warehouse.class, id);
        } finally {
            em.close();
        }
    }

    public int getWarehouseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Warehouse> rt = cq.from(Warehouse.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

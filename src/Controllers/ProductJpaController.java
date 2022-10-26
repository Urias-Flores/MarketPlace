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
import Models.Brand;
import Models.Category;
import Models.Warehouse;
import Models.Invoicedetail;
import java.util.ArrayList;
import java.util.List;
import Models.Buydetail;
import Models.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dell
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) {
        if (product.getInvoicedetailList() == null) {
            product.setInvoicedetailList(new ArrayList<Invoicedetail>());
        }
        if (product.getBuydetailList() == null) {
            product.setBuydetailList(new ArrayList<Buydetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Brand brand = product.getBrand();
            if (brand != null) {
                brand = em.getReference(brand.getClass(), brand.getBrandID());
                product.setBrand(brand);
            }
            Category category = product.getCategory();
            if (category != null) {
                category = em.getReference(category.getClass(), category.getCategoryID());
                product.setCategory(category);
            }
            Warehouse warehouse = product.getWarehouse();
            if (warehouse != null) {
                warehouse = em.getReference(warehouse.getClass(), warehouse.getWarehouseID());
                product.setWarehouse(warehouse);
            }
            List<Invoicedetail> attachedInvoicedetailList = new ArrayList<Invoicedetail>();
            for (Invoicedetail invoicedetailListInvoicedetailToAttach : product.getInvoicedetailList()) {
                invoicedetailListInvoicedetailToAttach = em.getReference(invoicedetailListInvoicedetailToAttach.getClass(), invoicedetailListInvoicedetailToAttach.getInvoiceDetailID());
                attachedInvoicedetailList.add(invoicedetailListInvoicedetailToAttach);
            }
            product.setInvoicedetailList(attachedInvoicedetailList);
            List<Buydetail> attachedBuydetailList = new ArrayList<Buydetail>();
            for (Buydetail buydetailListBuydetailToAttach : product.getBuydetailList()) {
                buydetailListBuydetailToAttach = em.getReference(buydetailListBuydetailToAttach.getClass(), buydetailListBuydetailToAttach.getBuyDetailID());
                attachedBuydetailList.add(buydetailListBuydetailToAttach);
            }
            product.setBuydetailList(attachedBuydetailList);
            em.persist(product);
            if (brand != null) {
                brand.getProductList().add(product);
                brand = em.merge(brand);
            }
            if (category != null) {
                category.getProductList().add(product);
                category = em.merge(category);
            }
            if (warehouse != null) {
                warehouse.getProductList().add(product);
                warehouse = em.merge(warehouse);
            }
            for (Invoicedetail invoicedetailListInvoicedetail : product.getInvoicedetailList()) {
                Product oldProductIDOfInvoicedetailListInvoicedetail = invoicedetailListInvoicedetail.getProductID();
                invoicedetailListInvoicedetail.setProductID(product);
                invoicedetailListInvoicedetail = em.merge(invoicedetailListInvoicedetail);
                if (oldProductIDOfInvoicedetailListInvoicedetail != null) {
                    oldProductIDOfInvoicedetailListInvoicedetail.getInvoicedetailList().remove(invoicedetailListInvoicedetail);
                    oldProductIDOfInvoicedetailListInvoicedetail = em.merge(oldProductIDOfInvoicedetailListInvoicedetail);
                }
            }
            for (Buydetail buydetailListBuydetail : product.getBuydetailList()) {
                Product oldProductIDOfBuydetailListBuydetail = buydetailListBuydetail.getProductID();
                buydetailListBuydetail.setProductID(product);
                buydetailListBuydetail = em.merge(buydetailListBuydetail);
                if (oldProductIDOfBuydetailListBuydetail != null) {
                    oldProductIDOfBuydetailListBuydetail.getBuydetailList().remove(buydetailListBuydetail);
                    oldProductIDOfBuydetailListBuydetail = em.merge(oldProductIDOfBuydetailListBuydetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getProducrID());
            Brand brandOld = persistentProduct.getBrand();
            Brand brandNew = product.getBrand();
            Category categoryOld = persistentProduct.getCategory();
            Category categoryNew = product.getCategory();
            Warehouse warehouseOld = persistentProduct.getWarehouse();
            Warehouse warehouseNew = product.getWarehouse();
            List<Invoicedetail> invoicedetailListOld = persistentProduct.getInvoicedetailList();
            List<Invoicedetail> invoicedetailListNew = product.getInvoicedetailList();
            List<Buydetail> buydetailListOld = persistentProduct.getBuydetailList();
            List<Buydetail> buydetailListNew = product.getBuydetailList();
            List<String> illegalOrphanMessages = null;
            for (Invoicedetail invoicedetailListOldInvoicedetail : invoicedetailListOld) {
                if (!invoicedetailListNew.contains(invoicedetailListOldInvoicedetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Invoicedetail " + invoicedetailListOldInvoicedetail + " since its productID field is not nullable.");
                }
            }
            for (Buydetail buydetailListOldBuydetail : buydetailListOld) {
                if (!buydetailListNew.contains(buydetailListOldBuydetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Buydetail " + buydetailListOldBuydetail + " since its productID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (brandNew != null) {
                brandNew = em.getReference(brandNew.getClass(), brandNew.getBrandID());
                product.setBrand(brandNew);
            }
            if (categoryNew != null) {
                categoryNew = em.getReference(categoryNew.getClass(), categoryNew.getCategoryID());
                product.setCategory(categoryNew);
            }
            if (warehouseNew != null) {
                warehouseNew = em.getReference(warehouseNew.getClass(), warehouseNew.getWarehouseID());
                product.setWarehouse(warehouseNew);
            }
            List<Invoicedetail> attachedInvoicedetailListNew = new ArrayList<Invoicedetail>();
            for (Invoicedetail invoicedetailListNewInvoicedetailToAttach : invoicedetailListNew) {
                invoicedetailListNewInvoicedetailToAttach = em.getReference(invoicedetailListNewInvoicedetailToAttach.getClass(), invoicedetailListNewInvoicedetailToAttach.getInvoiceDetailID());
                attachedInvoicedetailListNew.add(invoicedetailListNewInvoicedetailToAttach);
            }
            invoicedetailListNew = attachedInvoicedetailListNew;
            product.setInvoicedetailList(invoicedetailListNew);
            List<Buydetail> attachedBuydetailListNew = new ArrayList<Buydetail>();
            for (Buydetail buydetailListNewBuydetailToAttach : buydetailListNew) {
                buydetailListNewBuydetailToAttach = em.getReference(buydetailListNewBuydetailToAttach.getClass(), buydetailListNewBuydetailToAttach.getBuyDetailID());
                attachedBuydetailListNew.add(buydetailListNewBuydetailToAttach);
            }
            buydetailListNew = attachedBuydetailListNew;
            product.setBuydetailList(buydetailListNew);
            product = em.merge(product);
            if (brandOld != null && !brandOld.equals(brandNew)) {
                brandOld.getProductList().remove(product);
                brandOld = em.merge(brandOld);
            }
            if (brandNew != null && !brandNew.equals(brandOld)) {
                brandNew.getProductList().add(product);
                brandNew = em.merge(brandNew);
            }
            if (categoryOld != null && !categoryOld.equals(categoryNew)) {
                categoryOld.getProductList().remove(product);
                categoryOld = em.merge(categoryOld);
            }
            if (categoryNew != null && !categoryNew.equals(categoryOld)) {
                categoryNew.getProductList().add(product);
                categoryNew = em.merge(categoryNew);
            }
            if (warehouseOld != null && !warehouseOld.equals(warehouseNew)) {
                warehouseOld.getProductList().remove(product);
                warehouseOld = em.merge(warehouseOld);
            }
            if (warehouseNew != null && !warehouseNew.equals(warehouseOld)) {
                warehouseNew.getProductList().add(product);
                warehouseNew = em.merge(warehouseNew);
            }
            for (Invoicedetail invoicedetailListNewInvoicedetail : invoicedetailListNew) {
                if (!invoicedetailListOld.contains(invoicedetailListNewInvoicedetail)) {
                    Product oldProductIDOfInvoicedetailListNewInvoicedetail = invoicedetailListNewInvoicedetail.getProductID();
                    invoicedetailListNewInvoicedetail.setProductID(product);
                    invoicedetailListNewInvoicedetail = em.merge(invoicedetailListNewInvoicedetail);
                    if (oldProductIDOfInvoicedetailListNewInvoicedetail != null && !oldProductIDOfInvoicedetailListNewInvoicedetail.equals(product)) {
                        oldProductIDOfInvoicedetailListNewInvoicedetail.getInvoicedetailList().remove(invoicedetailListNewInvoicedetail);
                        oldProductIDOfInvoicedetailListNewInvoicedetail = em.merge(oldProductIDOfInvoicedetailListNewInvoicedetail);
                    }
                }
            }
            for (Buydetail buydetailListNewBuydetail : buydetailListNew) {
                if (!buydetailListOld.contains(buydetailListNewBuydetail)) {
                    Product oldProductIDOfBuydetailListNewBuydetail = buydetailListNewBuydetail.getProductID();
                    buydetailListNewBuydetail.setProductID(product);
                    buydetailListNewBuydetail = em.merge(buydetailListNewBuydetail);
                    if (oldProductIDOfBuydetailListNewBuydetail != null && !oldProductIDOfBuydetailListNewBuydetail.equals(product)) {
                        oldProductIDOfBuydetailListNewBuydetail.getBuydetailList().remove(buydetailListNewBuydetail);
                        oldProductIDOfBuydetailListNewBuydetail = em.merge(oldProductIDOfBuydetailListNewBuydetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = product.getProducrID();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProducrID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Invoicedetail> invoicedetailListOrphanCheck = product.getInvoicedetailList();
            for (Invoicedetail invoicedetailListOrphanCheckInvoicedetail : invoicedetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Invoicedetail " + invoicedetailListOrphanCheckInvoicedetail + " in its invoicedetailList field has a non-nullable productID field.");
            }
            List<Buydetail> buydetailListOrphanCheck = product.getBuydetailList();
            for (Buydetail buydetailListOrphanCheckBuydetail : buydetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Buydetail " + buydetailListOrphanCheckBuydetail + " in its buydetailList field has a non-nullable productID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Brand brand = product.getBrand();
            if (brand != null) {
                brand.getProductList().remove(product);
                brand = em.merge(brand);
            }
            Category category = product.getCategory();
            if (category != null) {
                category.getProductList().remove(product);
                category = em.merge(category);
            }
            Warehouse warehouse = product.getWarehouse();
            if (warehouse != null) {
                warehouse.getProductList().remove(product);
                warehouse = em.merge(warehouse);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

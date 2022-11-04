package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.Brand;
import Models.Category;
import Models.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) {
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
            em.persist(product);
            if (brand != null) {
                brand.getProductList().add(product);
                brand = em.merge(brand);
            }
            if (category != null) {
                category.getProductList().add(product);
                category = em.merge(category);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getProductID());
            Brand brandOld = persistentProduct.getBrand();
            Brand brandNew = product.getBrand();
            Category categoryOld = persistentProduct.getCategory();
            Category categoryNew = product.getCategory();
            if (brandNew != null) {
                brandNew = em.getReference(brandNew.getClass(), brandNew.getBrandID());
                product.setBrand(brandNew);
            }
            if (categoryNew != null) {
                categoryNew = em.getReference(categoryNew.getClass(), categoryNew.getCategoryID());
                product.setCategory(categoryNew);
            }
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = product.getProductID();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
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

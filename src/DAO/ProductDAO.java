package DAO;

import Controllers.ProductJpaController;
import Controllers.exceptions.NonexistentEntityException;
import Models.Brand;
import Models.Category;
import Models.Product;
import Resourse.Conection;
import javax.persistence.EntityManagerFactory;

public class ProductDAO {
    
    private EntityManagerFactory emf = null;
    private ProductJpaController productJpaController = null; 
    
    private int ProductID;
    private String Description;
    private Brand BrandID;
    private Category CategoryID;
    private float PriceBuy;
    private float PriceSale;
    private byte[] Imagen;
    private String ImageExtension;
    private String Barcode;
    
    public void ProductDAO(){
        this.emf = Conection.CreateEntityManager();
        this.productJpaController = new ProductJpaController(emf);
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Brand getBrandID() {
        return BrandID;
    }

    public void setBrandID(Brand BrandID) {
        this.BrandID = BrandID;
    }

    public Category getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(Category CategoryID) {
        this.CategoryID = CategoryID;
    }

    public float getPriceBuy() {
        return PriceBuy;
    }

    public void setPriceBuy(float PriceBuy) {
        this.PriceBuy = PriceBuy;
    }

    public float getPriceSale() {
        return PriceSale;
    }

    public void setPriceSale(float PriceSale) {
        this.PriceSale = PriceSale;
    }

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] Imagen) {
        this.Imagen = Imagen;
    }

    public String getImageExtension() {
        return ImageExtension;
    }

    public void setImageExtension(String ImageExtension) {
        this.ImageExtension = ImageExtension;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String Barcode) {
        this.Barcode = Barcode;
    }
    
    public void save(){
        Product product = new Product();
        
        product.setDescription(Description);
        product.setBrand(BrandID);
        product.setCategory(CategoryID);
        product.setPriceBuy(PriceBuy);
        product.setPriceSale(PriceSale);
        product.setImage(Imagen);
        product.setImageExtension(ImageExtension);
        product.setBarcode(Barcode);
        
        productJpaController.create(product);
    }
    
    public boolean edit(){
        Product product = new Product();
        
        product.setProductID(ProductID);
        product.setDescription(Description);
        product.setBrand(BrandID);
        product.setCategory(CategoryID);
        product.setPriceBuy(PriceBuy);
        product.setPriceSale(PriceSale);
        product.setImage(Imagen);
        product.setImageExtension(ImageExtension);
        product.setBarcode(Barcode);
        
        try {
            productJpaController.edit(product);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean delete(){
        try {
            productJpaController.destroy(ProductID);
        } catch (NonexistentEntityException e) {
            return false;
        }
        return true;
    }
}

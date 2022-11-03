package DAO;

public class ProductDAO {
    
    private int ProductID;
    private String Description;
    private int BrandID;
    private int CategoryID;
    private float PriceBuy;
    private float PriceSale;
    private byte[] Imagen;
    private String ImageExtension;
    private String Barcode;

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

    public int getBrandID() {
        return BrandID;
    }

    public void setBrandID(int BrandID) {
        this.BrandID = BrandID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
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
    
    public void create(){
        
    }
    
    public boolean edit(){
        try {
            
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean delete(){
        try {
            
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductID", query = "SELECT p FROM Product p WHERE p.productID = :productID"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByPriceBuy", query = "SELECT p FROM Product p WHERE p.priceBuy = :priceBuy"),
    @NamedQuery(name = "Product.findByPriceSale", query = "SELECT p FROM Product p WHERE p.priceSale = :priceSale"),
    @NamedQuery(name = "Product.findByImageExtension", query = "SELECT p FROM Product p WHERE p.imageExtension = :imageExtension"),
    @NamedQuery(name = "Product.findByBarcode", query = "SELECT p FROM Product p WHERE p.barcode = :barcode")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ProductID")
    private Integer productID;
    @Basic(optional = false)
    @Column(name = "Description")
    private String description;
    @Basic(optional = false)
    @Column(name = "PriceBuy")
    private float priceBuy;
    @Basic(optional = false)
    @Column(name = "PriceSale")
    private float priceSale;
    @Basic(optional = false)
    @Lob
    @Column(name = "Image")
    private byte[] image;
    @Basic(optional = false)
    @Column(name = "ImageExtension")
    private String imageExtension;
    @Column(name = "Barcode")
    private String barcode;
    @JoinColumns({
        @JoinColumn(name = "BrandID", referencedColumnName = "BrandID"),
        @JoinColumn(name = "BrandID", referencedColumnName = "BrandID")})
    @ManyToOne(optional = false)
    private Brand brand;
    @JoinColumns({
        @JoinColumn(name = "CategoryID", referencedColumnName = "CategoryID"),
        @JoinColumn(name = "CategoryID", referencedColumnName = "CategoryID")})
    @ManyToOne(optional = false)
    private Category category;

    public Product() {
    }

    public Product(Integer productID) {
        this.productID = productID;
    }

    public Product(Integer productID, String description, float priceBuy, float priceSale, byte[] image, String imageExtension) {
        this.productID = productID;
        this.description = description;
        this.priceBuy = priceBuy;
        this.priceSale = priceSale;
        this.image = image;
        this.imageExtension = imageExtension;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(float priceBuy) {
        this.priceBuy = priceBuy;
    }

    public float getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(float priceSale) {
        this.priceSale = priceSale;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productID != null ? productID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productID == null && other.productID != null) || (this.productID != null && !this.productID.equals(other.productID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Product[ productID=" + productID + " ]";
    }
    
}

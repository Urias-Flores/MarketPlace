package Models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "buydetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buydetail.findAll", query = "SELECT b FROM Buydetail b"),
    @NamedQuery(name = "Buydetail.findByBuyDetailID", query = "SELECT b FROM Buydetail b WHERE b.buyDetailID = :buyDetailID"),
    @NamedQuery(name = "Buydetail.findByAmount", query = "SELECT b FROM Buydetail b WHERE b.amount = :amount"),
    @NamedQuery(name = "Buydetail.findByPrice", query = "SELECT b FROM Buydetail b WHERE b.price = :price"),
    @NamedQuery(name = "Buydetail.findBySt", query = "SELECT b FROM Buydetail b WHERE b.st = :st"),
    @NamedQuery(name = "Buydetail.findByDiscount", query = "SELECT b FROM Buydetail b WHERE b.discount = :discount"),
    @NamedQuery(name = "Buydetail.findBySubtotal", query = "SELECT b FROM Buydetail b WHERE b.subtotal = :subtotal")})
public class Buydetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BuyDetailID")
    private Integer buyDetailID;
    @Basic(optional = false)
    @Column(name = "Amount")
    private int amount;
    @Basic(optional = false)
    @Column(name = "Price")
    private float price;
    @Basic(optional = false)
    @Column(name = "ST")
    private float st;
    @Basic(optional = false)
    @Column(name = "Discount")
    private float discount;
    @Basic(optional = false)
    @Column(name = "Subtotal")
    private float subtotal;
    @JoinColumn(name = "BuyID", referencedColumnName = "BuyID")
    @ManyToOne(optional = false)
    private Buy buyID;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
    @ManyToOne(optional = false)
    private Product productID;

    public Buydetail() {
    }

    public Buydetail(Integer buyDetailID) {
        this.buyDetailID = buyDetailID;
    }

    public Buydetail(Integer buyDetailID, int amount, float price, float st, float discount, float subtotal) {
        this.buyDetailID = buyDetailID;
        this.amount = amount;
        this.price = price;
        this.st = st;
        this.discount = discount;
        this.subtotal = subtotal;
    }

    public Integer getBuyDetailID() {
        return buyDetailID;
    }

    public void setBuyDetailID(Integer buyDetailID) {
        this.buyDetailID = buyDetailID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSt() {
        return st;
    }

    public void setSt(float st) {
        this.st = st;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public Buy getBuyID() {
        return buyID;
    }

    public void setBuyID(Buy buyID) {
        this.buyID = buyID;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buyDetailID != null ? buyDetailID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buydetail)) {
            return false;
        }
        Buydetail other = (Buydetail) object;
        if ((this.buyDetailID == null && other.buyDetailID != null) || (this.buyDetailID != null && !this.buyDetailID.equals(other.buyDetailID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Buydetail[ buyDetailID=" + buyDetailID + " ]";
    }
    
}

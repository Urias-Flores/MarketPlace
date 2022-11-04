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

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "invoicedetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoicedetail.findAll", query = "SELECT i FROM Invoicedetail i"),
    @NamedQuery(name = "Invoicedetail.findByInvoiceDetailID", query = "SELECT i FROM Invoicedetail i WHERE i.invoiceDetailID = :invoiceDetailID"),
    @NamedQuery(name = "Invoicedetail.findByAmount", query = "SELECT i FROM Invoicedetail i WHERE i.amount = :amount"),
    @NamedQuery(name = "Invoicedetail.findByPrice", query = "SELECT i FROM Invoicedetail i WHERE i.price = :price"),
    @NamedQuery(name = "Invoicedetail.findBySt", query = "SELECT i FROM Invoicedetail i WHERE i.st = :st"),
    @NamedQuery(name = "Invoicedetail.findByDiscount", query = "SELECT i FROM Invoicedetail i WHERE i.discount = :discount"),
    @NamedQuery(name = "Invoicedetail.findBySubtotal", query = "SELECT i FROM Invoicedetail i WHERE i.subtotal = :subtotal")})
public class Invoicedetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "InvoiceDetailID")
    private Integer invoiceDetailID;
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
    @JoinColumn(name = "InvoiceID", referencedColumnName = "InvoiveID")
    @ManyToOne(optional = false)
    private Invoice invoiceID;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
    @ManyToOne(optional = false)
    private Product productID;

    public Invoicedetail() {
    }

    public Invoicedetail(Integer invoiceDetailID) {
        this.invoiceDetailID = invoiceDetailID;
    }

    public Invoicedetail(Integer invoiceDetailID, int amount, float price, float st, float discount, float subtotal) {
        this.invoiceDetailID = invoiceDetailID;
        this.amount = amount;
        this.price = price;
        this.st = st;
        this.discount = discount;
        this.subtotal = subtotal;
    }

    public Integer getInvoiceDetailID() {
        return invoiceDetailID;
    }

    public void setInvoiceDetailID(Integer invoiceDetailID) {
        this.invoiceDetailID = invoiceDetailID;
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

    public Invoice getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Invoice invoiceID) {
        this.invoiceID = invoiceID;
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
        hash += (invoiceDetailID != null ? invoiceDetailID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoicedetail)) {
            return false;
        }
        Invoicedetail other = (Invoicedetail) object;
        if ((this.invoiceDetailID == null && other.invoiceDetailID != null) || (this.invoiceDetailID != null && !this.invoiceDetailID.equals(other.invoiceDetailID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Invoicedetail[ invoiceDetailID=" + invoiceDetailID + " ]";
    }
    
}

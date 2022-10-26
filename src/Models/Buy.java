/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "buy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Buy.findAll", query = "SELECT b FROM Buy b"),
    @NamedQuery(name = "Buy.findByBuyID", query = "SELECT b FROM Buy b WHERE b.buyID = :buyID"),
    @NamedQuery(name = "Buy.findByDate", query = "SELECT b FROM Buy b WHERE b.date = :date"),
    @NamedQuery(name = "Buy.findByTime", query = "SELECT b FROM Buy b WHERE b.time = :time")})
public class Buy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BuyID")
    private Integer buyID;
    @Basic(optional = false)
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "Time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @JoinColumns({
        @JoinColumn(name = "SupplierID", referencedColumnName = "SupplierID"),
        @JoinColumn(name = "SupplierID", referencedColumnName = "SupplierID"),
        @JoinColumn(name = "SupplierID", referencedColumnName = "SupplierID")})
    @ManyToOne(optional = false)
    private Supplier supplier;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyID")
    private List<Buydetail> buydetailList;

    public Buy() {
    }

    public Buy(Integer buyID) {
        this.buyID = buyID;
    }

    public Buy(Integer buyID, Date date, Date time) {
        this.buyID = buyID;
        this.date = date;
        this.time = time;
    }

    public Integer getBuyID() {
        return buyID;
    }

    public void setBuyID(Integer buyID) {
        this.buyID = buyID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @XmlTransient
    public List<Buydetail> getBuydetailList() {
        return buydetailList;
    }

    public void setBuydetailList(List<Buydetail> buydetailList) {
        this.buydetailList = buydetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buyID != null ? buyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Buy)) {
            return false;
        }
        Buy other = (Buy) object;
        if ((this.buyID == null && other.buyID != null) || (this.buyID != null && !this.buyID.equals(other.buyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Buy[ buyID=" + buyID + " ]";
    }
    
}

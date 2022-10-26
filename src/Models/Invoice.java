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
@Table(name = "invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findByInvoiveID", query = "SELECT i FROM Invoice i WHERE i.invoiveID = :invoiveID"),
    @NamedQuery(name = "Invoice.findByDate", query = "SELECT i FROM Invoice i WHERE i.date = :date"),
    @NamedQuery(name = "Invoice.findByTime", query = "SELECT i FROM Invoice i WHERE i.time = :time")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "InvoiveID")
    private Integer invoiveID;
    @Basic(optional = false)
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "Time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceID")
    private List<Invoicedetail> invoicedetailList;
    @JoinColumns({
        @JoinColumn(name = "ClientID", referencedColumnName = "ClientID"),
        @JoinColumn(name = "ClientID", referencedColumnName = "ClientID"),
        @JoinColumn(name = "ClientID", referencedColumnName = "ClientID")})
    @ManyToOne(optional = false)
    private Clients clients;
    @JoinColumns({
        @JoinColumn(name = "UserID", referencedColumnName = "UserID"),
        @JoinColumn(name = "UserID", referencedColumnName = "UserID"),
        @JoinColumn(name = "UserID", referencedColumnName = "UserID")})
    @ManyToOne(optional = false)
    private Users users;

    public Invoice() {
    }

    public Invoice(Integer invoiveID) {
        this.invoiveID = invoiveID;
    }

    public Invoice(Integer invoiveID, Date date, Date time) {
        this.invoiveID = invoiveID;
        this.date = date;
        this.time = time;
    }

    public Integer getInvoiveID() {
        return invoiveID;
    }

    public void setInvoiveID(Integer invoiveID) {
        this.invoiveID = invoiveID;
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

    @XmlTransient
    public List<Invoicedetail> getInvoicedetailList() {
        return invoicedetailList;
    }

    public void setInvoicedetailList(List<Invoicedetail> invoicedetailList) {
        this.invoicedetailList = invoicedetailList;
    }

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiveID != null ? invoiveID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.invoiveID == null && other.invoiveID != null) || (this.invoiveID != null && !this.invoiveID.equals(other.invoiveID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Invoice[ invoiveID=" + invoiveID + " ]";
    }
    
}

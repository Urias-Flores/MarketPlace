/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "warehouse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Warehouse.findAll", query = "SELECT w FROM Warehouse w"),
    @NamedQuery(name = "Warehouse.findByWarehouseID", query = "SELECT w FROM Warehouse w WHERE w.warehouseID = :warehouseID"),
    @NamedQuery(name = "Warehouse.findByName", query = "SELECT w FROM Warehouse w WHERE w.name = :name")})
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "WarehouseID")
    private Integer warehouseID;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @OneToMany(mappedBy = "warehouse")
    private List<Product> productList;
    @JoinColumns({
        @JoinColumn(name = "EmployeeID", referencedColumnName = "EmpleyeeID"),
        @JoinColumn(name = "EmployeeID", referencedColumnName = "EmpleyeeID"),
        @JoinColumn(name = "EmployeeID", referencedColumnName = "EmpleyeeID")})
    @ManyToOne(optional = false)
    private Employee employee;

    public Warehouse() {
    }

    public Warehouse(Integer warehouseID) {
        this.warehouseID = warehouseID;
    }

    public Warehouse(Integer warehouseID, String name) {
        this.warehouseID = warehouseID;
        this.name = name;
    }

    public Integer getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Integer warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (warehouseID != null ? warehouseID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Warehouse)) {
            return false;
        }
        Warehouse other = (Warehouse) object;
        if ((this.warehouseID == null && other.warehouseID != null) || (this.warehouseID != null && !this.warehouseID.equals(other.warehouseID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Warehouse[ warehouseID=" + warehouseID + " ]";
    }
    
}

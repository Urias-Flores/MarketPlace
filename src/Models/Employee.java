package Models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findByEmpleyeeID", query = "SELECT e FROM Employee e WHERE e.empleyeeID = :empleyeeID"),
    @NamedQuery(name = "Employee.findByName", query = "SELECT e FROM Employee e WHERE e.name = :name"),
    @NamedQuery(name = "Employee.findByDni", query = "SELECT e FROM Employee e WHERE e.dni = :dni"),
    @NamedQuery(name = "Employee.findByDate", query = "SELECT e FROM Employee e WHERE e.date = :date"),
    @NamedQuery(name = "Employee.findByRole", query = "SELECT e FROM Employee e WHERE e.role = :role"),
    @NamedQuery(name = "Employee.findByPayment", query = "SELECT e FROM Employee e WHERE e.payment = :payment")})
public class Employee implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Collection<Warehouse> warehouseCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EmpleyeeID")
    private Integer empleyeeID;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "DNI")
    private String dni;
    @Basic(optional = false)
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "Role")
    private int role;
    @Basic(optional = false)
    @Column(name = "Payment")
    private float payment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Collection<Users> usersCollection;

    public Employee() {
    }

    public Employee(Integer empleyeeID) {
        this.empleyeeID = empleyeeID;
    }

    public Employee(Integer empleyeeID, String name, String dni, Date date, int role, float payment) {
        this.empleyeeID = empleyeeID;
        this.name = name;
        this.dni = dni;
        this.date = date;
        this.role = role;
        this.payment = payment;
    }

    public Integer getEmpleyeeID() {
        return empleyeeID;
    }

    public void setEmpleyeeID(Integer empleyeeID) {
        this.empleyeeID = empleyeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empleyeeID != null ? empleyeeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.empleyeeID == null && other.empleyeeID != null) || (this.empleyeeID != null && !this.empleyeeID.equals(other.empleyeeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Employee[ empleyeeID=" + empleyeeID + " ]";
    }

    @XmlTransient
    public Collection<Warehouse> getWarehouseCollection() {
        return warehouseCollection;
    }

    public void setWarehouseCollection(Collection<Warehouse> warehouseCollection) {
        this.warehouseCollection = warehouseCollection;
    }
    
}

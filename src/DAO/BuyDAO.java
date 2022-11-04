package DAO;

import Controllers.BuyJpaController;
import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Models.Buy;
import Models.Buydetail;
import Models.Supplier;
import Models.Users;
import Resourse.Conection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

public class BuyDAO {
    
    private EntityManagerFactory emf = null;
    private BuyJpaController buyJpaController = null;
    private List<Buydetail> buydetailList = null;
    
    private int BuyID;
    private Date Date;
    private Time Time;
    private Users UserID;
    private Supplier SupplierID;

    private BuyDAO(){
        emf = Conection.CreateEntityManager();
        buyJpaController = new BuyJpaController(emf);
    }

    public List<Buydetail> getBuydetailList() {
        return buydetailList;
    }

    public void setBuydetailList(List<Buydetail> buydetailList) {
        this.buydetailList = buydetailList;
    }

    public int getBuyID() {
        return BuyID;
    }

    public void setBuyID(int BuyID) {
        this.BuyID = BuyID;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public Time getTime() {
        return Time;
    }

    public void setTime(Time Time) {
        this.Time = Time;
    }

    public Users getUserID() {
        return UserID;
    }

    public void setUserID(Users UserID) {
        this.UserID = UserID;
    }

    public Supplier getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(Supplier SupplierID) {
        this.SupplierID = SupplierID;
    }
    
    public void save(){
        Buy buy = new Buy();

        buy.setDate(Date);
        buy.setTime(Time);
        buy.setUsers(UserID);
        buy.setSupplier(SupplierID);
        buy.setBuydetailList(buydetailList);

        buyJpaController.create(buy);
    }
    
    public boolean edit(){
        Buy buy = new Buy();
        
        buy.setBuyID(BuyID);
        buy.setDate(Date);
        buy.setTime(Time);
        buy.setUsers(UserID);
        buy.setSupplier(SupplierID);
        
        try {
            buyJpaController.edit(buy);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BuyDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(BuyDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean delete(){
        try {
            buyJpaController.destroy(BuyID);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(BuyDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    } 
}

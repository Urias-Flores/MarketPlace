package DAO;

import Controllers.BrandJpaController;
import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Models.Brand;
import Resourse.Conection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

public class BrandDAO {
    
    private EntityManagerFactory emf = null;
    private BrandJpaController brandJpaController = null;
    
    private int BrandID;
    private String Name;
    
    public BrandDAO(){
        this.emf = Conection.CreateEntityManager();
        brandJpaController = new BrandJpaController(emf);
    }

    public int getBrandID() {
        return BrandID;
    }

    public void setBrandID(int BrandID) {
        this.BrandID = BrandID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
    public ArrayList<Brand> select(){
        ArrayList<Brand> list = new ArrayList<>();
        list.addAll(brandJpaController.findBrandEntities());
        return list;
    }
    
    public void save(){
        Brand brand = new Brand();
        brand.setName(Name);
        brandJpaController.create(brand);
    }
    
    public boolean edit(){
        Brand brand = new Brand();
        brand.setBrandID(BrandID);
        brand.setName(Name);
        
        try {
            brandJpaController.edit(brand);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean delete(){
        try {
            brandJpaController.destroy(BrandID);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(BrandDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}

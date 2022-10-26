package DAO;

import Controllers.BrandJpaController;
import Models.Brand;
import Resourse.Conection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class BrandDAO {
    
    EntityManagerFactory emf = null;
    BrandJpaController brandJpaController = null;
    
    private int BrandID;
    private String Name;
    
    public BrandDAO(){
        EntityManager em =Conection.CreateEntityManager();
        //brandJpaController = new BrandJpaController(em);
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
    
    public void save(){
        Brand brand = new Brand();
        brand.setName(Name);
        brandJpaController.create(brand);
    }
}

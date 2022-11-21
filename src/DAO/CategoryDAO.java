package DAO;

import Controllers.CategoryJpaController;
import Models.Category;
import Resourse.Conection;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;

public class CategoryDAO {
    
    private EntityManagerFactory emf = null;
    private CategoryJpaController categoryJpaController = null;
      
    private int CategoryID;
    private String Name;

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
    public CategoryDAO(){
        this.emf = Conection.CreateEntityManager();
        this.categoryJpaController = new CategoryJpaController(emf);
    }
    
    public ArrayList<Category> select(){
        ArrayList<Category> list = new ArrayList<>();
        list.addAll(categoryJpaController.findCategoryEntities());
        return list;
    }
    
    public void save(){
        Category category = new Category();
        category.setName(Name);
        
        categoryJpaController.create(category);
    }
    
    public void edit(){
        
    }
    
    public void delete(){
        
    }
    
}
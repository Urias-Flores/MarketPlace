package VO;

import DAO.CategoryDAO;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddCategoryVO {

    private JTextField Name;
    private JLabel Error;
            
    private final CategoryDAO categoryDAO;

    public AddCategoryVO(JTextField Name, JLabel Error) {
        this.Name = Name;
        this.Error = Error;
        categoryDAO = new CategoryDAO();
    }
    
    public void saveCategory(){
        categoryDAO.setName(Name.getText());
        categoryDAO.save();
    }
    
    public void editCategory(){
        categoryDAO.setCategoryID(Integer.parseInt(Name.getName()));
        categoryDAO.setName(Name.getText());
        
        categoryDAO.edit();
    }
    
    public void deleteCategory(){
        categoryDAO.setCategoryID(Integer.parseInt(Name.getName()));
        
        categoryDAO.delete();
    }
    
    public boolean validate(){
        if(Name.getText().isEmpty()){
            Error.setText("El nombre de la categoria es obligatorio");
            return false;
        }
        return true;
    }
}

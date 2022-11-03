package VO;

import DAO.BrandDAO;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddBrandVO {
    
    private JTextField Name;
    private JLabel Error;
            
    private final BrandDAO brandDAO;

    public AddBrandVO(JTextField Name, JLabel Error) {
        this.Name = Name;
        this.Error = Error;
        brandDAO = new BrandDAO();
    }
    
    public void saveBrand(){
        brandDAO.setName(Name.getText());
        
        brandDAO.save();
    }
    
    public void editBrand(){
        brandDAO.setBrandID(Integer.parseInt(Name.getName()));
        brandDAO.setName(Name.getText());
        
        brandDAO.edit();
    }
    
    public void deleteBrans(){
        brandDAO.setBrandID(Integer.parseInt(Name.getName()));
        
        brandDAO.delete();
    }
    
    public boolean validate(){
        if(Name.getText().isEmpty()){
            Error.setText("El nombre de la marca es obligatorio");
            return false;
        }
        return true;
    }
}

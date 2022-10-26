package VO;

import DAO.BrandDAO;
import javax.swing.JTextField;

public class AddBrandVO {
    
    private JTextField Name;

    public AddBrandVO(JTextField Name) {
        this.Name = Name;
    }
    
    public void saveBrand(){
        BrandDAO brandDAO = new BrandDAO();
        brandDAO.setName(Name.getText());
        if(Name.getName().isEmpty()){
            
            brandDAO.save();
        }else{
            brandDAO.setBrandID(Integer.parseInt(Name.getName()));
            
        }
    }
}

package VO;

import DAO.BrandDAO;
import DAO.CategoryDAO;
import Models.Brand;
import Models.Category;
import javafx.scene.control.ComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddProductVO {
    BrandDAO brandDAO = new BrandDAO();
    CategoryDAO categoryDAO = new CategoryDAO();
    
    JTextArea Description;
    JComboBox Brand;
    JComboBox Category;
    JTextField PriceBuy;
    JTextField PriceSale;
    JTextField Barcode;
    JLabel File;

    public AddProductVO(JTextArea Description, JComboBox Brand, JComboBox Category, JTextField PriceBuy, JTextField PriceSale, JTextField Barcode, JLabel File) {
        this.Description = Description;
        this.Brand = Brand;
        this.Category = Category;
        this.PriceBuy = PriceBuy;
        this.PriceSale = PriceSale;
        this.Barcode = Barcode;
        this.File = File;
    }
    
    public void Load(){
        DefaultComboBoxModel<Brand> modelBrand = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<Category> modelCategory = new DefaultComboBoxModel<>();
        
        modelCategory.addElement(new Category(0, "-- Seleccion una categoria --"));
        modelBrand.addElement(new Brand(0, "-- Seleccion una marca --"));
        
        brandDAO.select().forEach((brand)->{
            modelBrand.addElement(brand);
        });
        Brand.setModel(modelBrand);
        
        categoryDAO.select().forEach((category) -> {
            modelCategory.addElement(category);
        });
        Category.setModel(modelCategory);
    }
}

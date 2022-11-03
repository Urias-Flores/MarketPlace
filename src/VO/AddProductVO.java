package VO;

import Models.Brand;
import Models.Category;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddProductVO {
    JTextArea Description;
    JComboBox<Brand> Brand;
    JComboBox<Category> Category;
    JTextField PriceBuy;
    JTextField PriceSale;
    JTextField Barcode;
    JLabel File;
    
    
}

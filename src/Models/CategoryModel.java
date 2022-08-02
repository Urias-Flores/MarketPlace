package Models;

import Resourse.Conection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class CategoryModel {
    //General Variables
    private Conection conec = new Conection();
    private String URL;
    
    //DataBase Fields
    
    
    public Map<Integer, String> SelectCategory(){
        Map<Integer, String> map = new HashMap<>();
        URL = "SELECT * FROM Brand WHERE BrandID = ";
        try(ResultSet rs = conec.getStatement().executeQuery(URL)){
            rs.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
        return map;
    }
    
    public DefaultTableModel SelectAllCategory(){
        DefaultTableModel model = new DefaultTableModel();
        //Columns
        String[] Columns  = {"Codigo", "Nombre"};
        model.setColumnIdentifiers(Columns);
        try(ResultSet rs = conec.getStatement().executeQuery(URL)){
            while(rs.next()){
                Vector v = new Vector();
                
                model.addRow(v);
            }
            rs.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
        return model;
    }
    
    public void InsertCategory(){
        URL = "INSERT INTO Brand VALUES(?, ?)";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
    
    public void UpdateCategory(){
        URL = "UPDATE BRAND SET Name = ? WHERE BrandID = ?";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
    
    public void DeleteCategory(){
        URL = "DELETE FROM Brand WHERE BrandID = ?";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
}

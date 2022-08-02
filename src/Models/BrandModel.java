package Models;

import Resourse.Conection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class BrandModel {
    //General Variables
    private Conection conec = new Conection();
    private String URL;
    
    //DataBase Fields
    private int BrandID;
    private String Name;

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
    
    public Map<Integer, String> SelectBrand(){
        Map<Integer, String> map = new HashMap<>();
        URL = "SELECT * FROM Brand WHERE BrandID = "+BrandID;
        try(ResultSet rs = conec.getStatement().executeQuery(URL)){
            if(rs.next()){
                map.put(rs.getInt("BrandID"), rs.getString("Name"));
            }
            rs.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
        return map;
    }
    
    public DefaultTableModel SelectAllBrand(){
        DefaultTableModel model = new DefaultTableModel();
        //Columns
        String[] Columns  = {"Codigo", "Nombre"};
        model.setColumnIdentifiers(Columns);
        try(ResultSet rs = conec.getStatement().executeQuery(URL)){
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getInt("BrandID"));
                v.add(rs.getString("Name"));
                model.addRow(v);
            }
            rs.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
        return model;
    }
    
    public void InsertBrand(){
        URL = "INSERT INTO Brand VALUES(?, ?)";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            ps.setInt(1, BrandID);
            ps.setString(2, Name);
            ps.execute();
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
    
    public void UpdateBrand(){
        URL = "UPDATE BRAND SET Name = ? WHERE BrandID = ?";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            ps.setString(1, Name);
            ps.setInt(2, BrandID);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
    
    public void DeleteBrand(){
        URL = "DELETE FROM Brand WHERE BrandID = ?";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            ps.setInt(1, BrandID);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
}

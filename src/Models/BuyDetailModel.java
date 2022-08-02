package Models;

import Resourse.Conection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class BuyDetailModel {
   //General Variables
    private Conection conec = new Conection();
    private String URL;
    
    //DataBase Fields
    private int BuyDetailID;
    private int BuyID;
    private int ProductID;
    private int Amount;
    private float Price;
    private float ST;
    private float Discount;
    private float Subtotal;

    public int getBuyDetailID() {
        return BuyDetailID;
    }

    public void setBuyDetailID(int BuyDetailID) {
        this.BuyDetailID = BuyDetailID;
    }

    public int getBuyID() {
        return BuyID;
    }

    public void setBuyID(int BuyID) {
        this.BuyID = BuyID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public float getST() {
        return ST;
    }

    public void setST(float ST) {
        this.ST = ST;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float Discount) {
        this.Discount = Discount;
    }

    public float getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(float Subtotal) {
        this.Subtotal = Subtotal;
    }
    
    public ArrayList SelectBuyDetail(){
        ArrayList list = new ArrayList<>();
        URL = "SELECT * FROM BuyDetail WHERE BuyDetailID = "+BuyDetailID;
        try(ResultSet rs = conec.getStatement().executeQuery(URL)){
            if(rs.next()){
                list.add(rs.getInt("BuyDetailID"));
                list.add(rs.getInt("BuyID"));
                list.add(rs.getInt("ProductID"));
                list.add(rs.getInt("Amount"));
                list.add(rs.getFloat("Price"));
                list.add(rs.getFloat("ST"));
                list.add(rs.getFloat("Discount"));
                list.add(rs.getFloat("Subtotal"));
            }
            rs.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
        return list;
    }
    
    public DefaultTableModel SelectAllBuyDetail(){
        DefaultTableModel model = new DefaultTableModel();
        //Columns
        String[] Columns  = 
        {"Codigo/Compra", "Codigo/Factura", "Producto", "Cantidad", "Precio", "Impuesto", "Descuento", "Subtotal"};
        model.setColumnIdentifiers(Columns);
        try(ResultSet rs = conec.getStatement().executeQuery(URL)){
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getInt("BuyDetailID"));
                v.add(rs.getInt("BuyID"));
                v.add(rs.getInt("ProductID"));
                v.add(rs.getInt("Amount"));
                v.add(rs.getFloat("Price"));
                v.add(rs.getFloat("ST"));
                v.add(rs.getFloat("Discount"));
                v.add(rs.getFloat("Subtotal"));
                model.addRow(v);
            }
            rs.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
        return model;
    }
    
    public void InsertBuyDetail(){
        URL = "INSERT INTO BuyDetail VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
    
    public void UpdateBuyDetail(){
        URL = "UPDATE BRAND SET Name = ? WHERE BrandID = ?";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
    
    public void DeleteBuyDetail(){
        URL = "DELETE FROM BuyDetail WHERE BuyDetail = ?";
        try(PreparedStatement ps = conec.getconec().prepareStatement(URL)){
            ps.setInt(1, BuyDetailID);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException ex){
            System.out.println("Error code: "+ex.getErrorCode()+"\n Error: "+ex.getMessage());
        }
    }
}

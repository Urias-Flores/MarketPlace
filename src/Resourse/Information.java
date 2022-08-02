package Resourse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Information {
    
    LocalConection conec = new LocalConection();
    
    public String getData(String parameter)
    {
        String data = "No data";
        try(ResultSet rs = conec.getStatement().executeQuery("Select Data from ConfigApp where name = '"+parameter+"'"))
        {
            if(rs.next())
            {
                return rs.getString(1);
            }
            rs.close();
        }catch(SQLException ex)
        {
            System.out.println("Codigo de error: "+ex.getErrorCode()+" Error: "+ex.getMessage());
        }
        return data;
    }
    
    public void setData(String parameter, String Data)
    {
        String url = "update ConfigApp set Data = ? where Name = ?";
        try(PreparedStatement stm = conec.getconec().prepareStatement(url))
        {
            stm.setString(1, Data);
            stm.setString(2, parameter);
            stm.executeUpdate();
            stm.close();
        }catch(SQLException ex)
        {
            System.out.println("Codigo de error: "+ex.getErrorCode()+" Error: "+ex.getMessage());
        }
    }
}

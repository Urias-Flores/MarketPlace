
package Resourse;

import Resourse.Information;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conection {
    private final Information info = new Information();
    private static Connection conec;
    private static Statement stm;
    private String ip, port, user, password, driver, database;
    
    public Statement getStatement()
    {
        try{

            stm = conec.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        }catch(SQLException ex)
                {
                   System.out.print(ex.getMessage());
                }
        return stm;
    }
    
    public Connection getconec()
    {
        return conec;
    }
    
    public Conection()
    {
        if(conec == null)
        {
            ip = info.getData("URL");
            port = info.getData("PORT");
            user = info.getData("USER");
            password = info.getData("PASSWORD");
            driver = info.getData("DRIVER");
            database = info.getData("DATABASE");
            try{
            Class.forName(driver);
            String url = "jdbc:mysql://"+ip+":"+port+"/"+database;
            conec = DriverManager.getConnection(url, user , password);
            }catch(ClassNotFoundException | SQLException ex){
                System.out.print(ex.getMessage());
            }
        }
    }
    
    public Connection tryConexion(String ip, String port, String user, String password, String driver, String database)
    {
        Connection c = null;
        try{
            Class.forName(driver);
            String url = "jdbc:mysql://"+ip+":"+port+"/"+database;
            c = DriverManager.getConnection(url, user , password);
        }catch(ClassNotFoundException | SQLException ex){
            System.out.print(ex.getMessage());
        }
        return c;
    }
    
    public void closeConec() throws SQLException
    {
        conec.close();
        conec = null;
    }
}

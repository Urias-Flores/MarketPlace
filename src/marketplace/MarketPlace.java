package marketplace;

import Main.MarketPlaceTheme;
import Resourse.Conection;
import Views.*;
import javax.persistence.EntityManagerFactory;

public class MarketPlace {

    public static void main(String[] args) {
        MarketPlaceTheme.setup();
        EntityManagerFactory conec = Conection.CreateEntityManager();
        
        if(conec != null){
            new Main().setVisible(true);
        }else{
            System.out.print("Conexion al servidor no establecida");
        }
        
        //Loger m = new Loger();
        //m.setVisible(true);
    }
}

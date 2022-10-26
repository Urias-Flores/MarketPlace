package Resourse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conection {
    public static EntityManagerFactory emf = null;
    
    public static EntityManagerFactory CreateEntityManager(){
        try{
            if(emf == null){
                emf = Persistence.createEntityManagerFactory("MarketPlacePU");
            }
        }catch(Exception ex){
            System.out.print("Error: "+ex.getMessage());
        }
        return emf;
    }
    
    public static void Disconnect(EntityManager em){
        if(em != null){
            em.close();
            emf.close();
        }
    }
}

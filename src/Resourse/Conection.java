package Resourse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conection {
    public static EntityManagerFactory emf = null;
    
    public static EntityManager CreateEntityManager(){
        try{
            if(emf == null){
                emf = Persistence.createEntityManagerFactory("JPA_pruebaPU");
            }
        }catch(Exception ex){
            System.out.print("Error: "+ex.getMessage());
        }
        return emf.createEntityManager();
    }
    
    public static void Disconnect(EntityManager em){
        if(em != null){
            em.close();
            emf.close();
        }
    }
}

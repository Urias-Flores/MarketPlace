
import Resourse.Conection;
import javax.persistence.EntityManagerFactory;

public class TestConection {
    public static void main(String[] args){
        EntityManagerFactory emf = Conection.CreateEntityManager();
        
        if(emf != null){
            System.out.println("Test: Conexion establecida con exito");
            System.exit(0);
        }
        System.out.println("Test: Fallo de conexion");
    }
}

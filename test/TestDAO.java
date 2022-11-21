
import DAO.BrandDAO;

public class TestDAO {

    public static void main(String[] args) {
        BrandDAO brandDAO = new BrandDAO();
        
        brandDAO.select().forEach(System.out::println);
    }
    
}

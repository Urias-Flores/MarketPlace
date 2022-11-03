package Models;

import Models.Users;
import Models.Warehouse;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T14:45:50")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile SingularAttribute<Employee, Integer> empleyeeID;
    public static volatile SingularAttribute<Employee, Date> date;
    public static volatile ListAttribute<Employee, Users> usersList;
    public static volatile SingularAttribute<Employee, Integer> role;
    public static volatile ListAttribute<Employee, Warehouse> warehouseList;
    public static volatile SingularAttribute<Employee, String> name;
    public static volatile SingularAttribute<Employee, Float> payment;
    public static volatile SingularAttribute<Employee, Integer> dni;

}
package Models;

import Models.Employee;
import Models.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T15:16:28")
@StaticMetamodel(Warehouse.class)
public class Warehouse_ { 

    public static volatile SingularAttribute<Warehouse, Integer> warehouseID;
    public static volatile SingularAttribute<Warehouse, String> name;
    public static volatile SingularAttribute<Warehouse, Employee> employee;
    public static volatile ListAttribute<Warehouse, Product> productList;

}
package Models;

import Models.Buy;
import Models.Location;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T17:41:54")
@StaticMetamodel(Supplier.class)
public class Supplier_ { 

    public static volatile SingularAttribute<Supplier, Integer> number;
    public static volatile ListAttribute<Supplier, Buy> buyList;
    public static volatile SingularAttribute<Supplier, Integer> supplierID;
    public static volatile SingularAttribute<Supplier, String> contact;
    public static volatile SingularAttribute<Supplier, String> name;
    public static volatile SingularAttribute<Supplier, Location> location;
    public static volatile SingularAttribute<Supplier, String> email;

}
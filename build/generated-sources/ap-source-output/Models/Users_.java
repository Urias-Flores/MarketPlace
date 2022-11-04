package Models;

import Models.Employee;
import Models.Invoice;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T17:41:54")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile ListAttribute<Users, Invoice> invoiceList;
    public static volatile SingularAttribute<Users, Integer> number;
    public static volatile SingularAttribute<Users, String> code;
    public static volatile SingularAttribute<Users, String> acces;
    public static volatile SingularAttribute<Users, byte[]> state;
    public static volatile SingularAttribute<Users, String> userName;
    public static volatile SingularAttribute<Users, Employee> employee;
    public static volatile SingularAttribute<Users, Integer> userID;
    public static volatile SingularAttribute<Users, String> email;

}
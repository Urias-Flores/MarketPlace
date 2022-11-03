package Models;

import Models.Invoice;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T15:16:28")
@StaticMetamodel(Clients.class)
public class Clients_ { 

    public static volatile ListAttribute<Clients, Invoice> invoiceList;
    public static volatile SingularAttribute<Clients, Integer> number;
    public static volatile SingularAttribute<Clients, Integer> clientID;
    public static volatile SingularAttribute<Clients, String> name;
    public static volatile SingularAttribute<Clients, String> rtn;
    public static volatile SingularAttribute<Clients, String> dni;
    public static volatile SingularAttribute<Clients, String> email;

}
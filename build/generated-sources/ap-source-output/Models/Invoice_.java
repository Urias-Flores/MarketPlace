package Models;

import Models.Clients;
import Models.Invoicedetail;
import Models.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T15:16:28")
@StaticMetamodel(Invoice.class)
public class Invoice_ { 

    public static volatile SingularAttribute<Invoice, Date> date;
    public static volatile SingularAttribute<Invoice, Clients> clients;
    public static volatile ListAttribute<Invoice, Invoicedetail> invoicedetailList;
    public static volatile SingularAttribute<Invoice, Date> time;
    public static volatile SingularAttribute<Invoice, Users> users;
    public static volatile SingularAttribute<Invoice, Integer> invoiveID;

}
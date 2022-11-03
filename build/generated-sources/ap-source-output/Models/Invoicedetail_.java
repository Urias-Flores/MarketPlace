package Models;

import Models.Invoice;
import Models.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T15:16:28")
@StaticMetamodel(Invoicedetail.class)
public class Invoicedetail_ { 

    public static volatile SingularAttribute<Invoicedetail, Float> st;
    public static volatile SingularAttribute<Invoicedetail, Integer> amount;
    public static volatile SingularAttribute<Invoicedetail, Integer> invoiceDetailID;
    public static volatile SingularAttribute<Invoicedetail, Product> productID;
    public static volatile SingularAttribute<Invoicedetail, Float> price;
    public static volatile SingularAttribute<Invoicedetail, Float> subtotal;
    public static volatile SingularAttribute<Invoicedetail, Float> discount;
    public static volatile SingularAttribute<Invoicedetail, Invoice> invoiceID;

}
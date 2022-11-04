package Models;

import Models.Buy;
import Models.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T17:41:54")
@StaticMetamodel(Buydetail.class)
public class Buydetail_ { 

    public static volatile SingularAttribute<Buydetail, Float> st;
    public static volatile SingularAttribute<Buydetail, Integer> amount;
    public static volatile SingularAttribute<Buydetail, Product> productID;
    public static volatile SingularAttribute<Buydetail, Float> price;
    public static volatile SingularAttribute<Buydetail, Float> subtotal;
    public static volatile SingularAttribute<Buydetail, Integer> buyDetailID;
    public static volatile SingularAttribute<Buydetail, Float> discount;
    public static volatile SingularAttribute<Buydetail, Buy> buyID;

}
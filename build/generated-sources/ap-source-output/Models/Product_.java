package Models;

import Models.Brand;
import Models.Buydetail;
import Models.Category;
import Models.Invoicedetail;
import Models.Warehouse;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T14:45:50")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, byte[]> image;
    public static volatile SingularAttribute<Product, Float> priceBuy;
    public static volatile ListAttribute<Product, Invoicedetail> invoicedetailList;
    public static volatile SingularAttribute<Product, Integer> producrID;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, String> imageExtension;
    public static volatile SingularAttribute<Product, Float> priceSale;
    public static volatile SingularAttribute<Product, Warehouse> warehouse;
    public static volatile SingularAttribute<Product, Category> category;
    public static volatile ListAttribute<Product, Buydetail> buydetailList;
    public static volatile SingularAttribute<Product, String> barcode;
    public static volatile SingularAttribute<Product, Brand> brand;

}
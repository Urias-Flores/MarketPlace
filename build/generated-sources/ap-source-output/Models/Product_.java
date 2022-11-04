package Models;

import Models.Brand;
import Models.Category;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T17:41:54")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, byte[]> image;
    public static volatile SingularAttribute<Product, Integer> productID;
    public static volatile SingularAttribute<Product, Float> priceBuy;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, String> imageExtension;
    public static volatile SingularAttribute<Product, Float> priceSale;
    public static volatile SingularAttribute<Product, Category> category;
    public static volatile SingularAttribute<Product, String> barcode;
    public static volatile SingularAttribute<Product, Brand> brand;

}
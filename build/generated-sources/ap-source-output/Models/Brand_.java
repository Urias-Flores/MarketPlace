package Models;

import Models.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T17:41:54")
@StaticMetamodel(Brand.class)
public class Brand_ { 

    public static volatile SingularAttribute<Brand, Integer> brandID;
    public static volatile SingularAttribute<Brand, String> name;
    public static volatile ListAttribute<Brand, Product> productList;

}
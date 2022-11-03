package Models;

import Models.Buydetail;
import Models.Supplier;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-11-03T14:45:50")
@StaticMetamodel(Buy.class)
public class Buy_ { 

    public static volatile SingularAttribute<Buy, Date> date;
    public static volatile SingularAttribute<Buy, Supplier> supplier;
    public static volatile SingularAttribute<Buy, Integer> buyID;
    public static volatile SingularAttribute<Buy, Date> time;
    public static volatile ListAttribute<Buy, Buydetail> buydetailList;

}
package marketplace;

import Main.MarketPlaceTheme;
import Views.Loger;

public class MarketPlace {

    public static void main(String[] args) {
        MarketPlaceTheme.setup();
        Loger m = new Loger();
        m.setVisible(true);
    }
}

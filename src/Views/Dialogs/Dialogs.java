
package Views.Dialogs;

import Views.Dialogs.Control.AddProductDialog;

public class Dialogs {
    
    private static int DELETE_ICON = -1;
    private static int WARNIG_ICON = 0;
    private static int OK_ICON = 1;
    
    public static void ShowAddBrandDialog(){
        AddBrandDialog addBrandDialog = new AddBrandDialog(null, true);
        addBrandDialog.setVisible(true);
    }
    
    public static void ShowAddProductDialog(){
        AddProductDialog addProductDialog = new AddProductDialog(null, true);
        addProductDialog.setVisible(true);
    }
}

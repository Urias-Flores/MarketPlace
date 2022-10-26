
package Views.Dialogs;

import Views.Dialogs.Control.AddProductDialog;

public class Dialogs {
    
    public static boolean ShowAddBrandDialog(){
        AddBrandDialog addBrandDialog = new AddBrandDialog(null, true);
        addBrandDialog.setVisible(true);
        
        return true;
    }
    
    public static void ShowAddProductDialog(){
        AddProductDialog addProductDialog = new AddProductDialog(null, true);
        addProductDialog.setVisible(true);
    }
}

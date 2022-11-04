
package Views.Dialogs;

import Views.Dialogs.Control.AddProductDialog;

public class Dialogs {
    
    public static int ERROR_ICON = -3;
    public static int WARNING_ICON = -2;
    public static int DELETE_ICON = -1;
    public static int CANCEL_ICON = 0;
    public static int OK_ICON = 1;
    
    public static void ShowOKDialog(String Message, int Icon){
        OkDialog okDialog = new OkDialog(null, true);
        okDialog.setMessage(Message);
        okDialog.setIcon(Icon);
        okDialog.setVisible(true);
        okDialog.dispose();
    }
    
    public static boolean ShowOKCancelDialog(String Message, int Icon){
        OkCancelDialog okCancelDialog = new OkCancelDialog(null, true);
        okCancelDialog.setIcon(Icon);
        okCancelDialog.setMessage(Message);
        okCancelDialog.setVisible(true);
        return okCancelDialog.getValue();
    }
    
    public static void ShowAddBrandDialog(){
        AddBrandDialog addBrandDialog = new AddBrandDialog(null, true);
        addBrandDialog.setVisible(true);
        addBrandDialog.dispose();
    }
    
    public static void ShowAddProductDialog(){
        AddProductDialog addProductDialog = new AddProductDialog(null, true);
        addProductDialog.setVisible(true);
        addProductDialog.dispose();
    }
}


import Views.Dialogs.Dialogs;

public class TestDialogs {
    public static void main(String[] args) {
        System.out.println(Dialogs.ShowOKCancelDialog("Este es el mensaje, lo aceptas?", Dialogs.DELETE_ICON));
        System.exit(0);
    }
}

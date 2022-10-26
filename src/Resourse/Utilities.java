
package Resourse;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Time;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Utilities {
    private static String ActualUser;
    private static Time UltimateTime;
    private static MouseListener mlButtonBlue = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel lb = (JLabel) e.getComponent();
            lb.setBackground(new Color(29, 131, 133));
            lb.setForeground(Color.WHITE);
            lb.setEnabled(true);
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel lb = (JLabel) e.getComponent();
            if(!lb.isEnabled()){
                lb.setBackground(new Color(190, 190, 190));
                lb.setForeground(Color.white);
            }
        }
        @Override
        public void mouseExited(MouseEvent e) {
            JLabel lb = (JLabel) e.getComponent();
            if(!lb.isEnabled()){
                lb.setBackground(Color.white);
                lb.setForeground(Color.black);
            }
        }
    };
    private static MouseListener mlButtonGray = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel lb = (JLabel) e.getComponent();
            lb.setBackground(new Color(220, 220, 220));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel lb = (JLabel) e.getComponent();
            lb.setBackground(Color.white);
        }
    };
    private static FocusListener flSearchTextField = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            JTextField tf = (JTextField) e.getComponent();
            if(tf.getText().equals("Buscar...")){
                tf.setText("");
                tf.setForeground(Color.black);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField tf = (JTextField) e.getComponent();
            if(tf.getText().isEmpty()){
                tf.setText("Buscar...");
                tf.setForeground(new Color(153, 153, 153));
            }
        }
    };
    private static MouseListener mlButtonClose = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel lb = (JLabel) e.getComponent();
            lb.setBackground(Color.red);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            JLabel lb = (JLabel) e.getComponent();
            lb.setBackground(new Color(15,88,132));
        }
    };

    public static String getActualUser() {
        return ActualUser;
    }

    public static void setActualUser(String ActualUser) {
        Utilities.ActualUser = ActualUser;
    }

    public static Time getUltimateTime() {
        return UltimateTime;
    }

    public static void setUltimateTime(Time UltimateTime) {
        Utilities.UltimateTime = UltimateTime;
    }

    public static MouseListener getMlButtonBlue() {
        return mlButtonBlue;
    }

    public static MouseListener getMlButtonGray() {
        return mlButtonGray;
    }

    public static FocusListener getFlSearchTextField() {
        return flSearchTextField;
    } 

    public static MouseListener getMlButtonClose() {
        return mlButtonClose;
    }
    
    
}

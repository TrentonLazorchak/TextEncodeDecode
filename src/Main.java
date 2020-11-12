import javax.swing.*;
// set logger

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame caeserFrame = new CaeserFrame("CaeserCipher");
                caeserFrame.setResizable(false);
                caeserFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                caeserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                caeserFrame.setVisible(true);
            }
        });

    }
}

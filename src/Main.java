import javax.swing.*;
// set logger

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame welcomeFrame = new WelcomeFrame("Simple Text Encoder/Decoder");
                welcomeFrame.setResizable(false);
                welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcomeFrame.setVisible(true);
            }
        });

    }
}

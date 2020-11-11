import javax.swing.*;
// set logger

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame encode = new EncodeFrame("ImageEncoder");
                encode.setResizable(false);
                encode.setExtendedState(JFrame.MAXIMIZED_BOTH);
                encode.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                encode.setVisible(true);
            }
        });

    }
}

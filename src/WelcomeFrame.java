import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WelcomeFrame extends JFrame {
    WelcomeFrame(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // fonts
        Font titleFont = new Font("Times New Roman", Font.PLAIN, 100);
        Font btnFont = new Font("Times New Roman", Font.PLAIN, 50);
        Font creditFont = new Font("Times New Roman", Font.PLAIN, 12);

        // sizes
        //Dimension titleSize = new Dimension(2000,250);

        // create Swing components
        JLabel imageEncoder = new JLabel("<html><center>WELCOME TO THE<p>SIMPLE TEXT ENCODER/DECODER</center></html>");
        imageEncoder.setFont(titleFont);

        JButton encode = new JButton("Encode");
        encode.setFont(btnFont);

        JButton decode = new JButton("Decode");
        decode.setFont(btnFont);

        JLabel credit = new JLabel("Created by Trenton Lazorchak, Jake Howell, Jahlin Jean-Baptiste and Olanrewaju Arbisala.");
        credit.setFont(creditFont);
        credit.setHorizontalAlignment(JLabel.CENTER);

        // add Swing components to the gui
        //// Title /////////////////////////////////////////
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.weighty = 1.0;
        gc.weightx = 1.0;
        gc.insets = new Insets(0,0,100,0);

        gc.gridx = 1;
        gc.gridy = 0;
        add(imageEncoder, gc);

        //// Buttons ///////////////////////////////////////
        gc.ipadx = 200;
        gc.ipady = 50;
        gc.weightx = 1;
        gc.insets = new Insets(0,0,0,0);

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(encode, gc);

        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 1;
        gc.gridy = 1;
        add(decode, gc);

        //// Credits ////////////////////////////////////////
        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 1;
        gc.gridy = 2;
        add(credit, gc);

        // Add behaviors
        // Encode button pressed
        encode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create Encode Screen
                JFrame encodeFrame = new EncodeFrame("Encode");
                encodeFrame.setResizable(false);
                encodeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                encodeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                encodeFrame.setVisible(true);

                // Close welcome screen
                setVisible(false);
                dispose();
            }
        });

        // Decode button pressed
        decode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create decode screen
                JFrame decodeFrame = new DecodeFrame("Decode");
                decodeFrame.setResizable(false);
                decodeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                decodeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                decodeFrame.setVisible(true);

                // Close welcome screen
                setVisible(false);
                dispose();
            }
        });
    }
}

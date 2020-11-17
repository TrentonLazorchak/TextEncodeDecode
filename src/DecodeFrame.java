import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

class DecodeFrame extends JFrame {
    DecodeFrame(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        final String inputDefault = "<Input Text>";
        final String decodedMsgDefault = "<Decoded Message Result>";

        // fonts
        Font titleFont = new Font("Times New Roman",Font.PLAIN,50);
        Font btnFont = new Font("Times New Roman", Font.PLAIN, 20);
        Font lblFont = new Font("Times New Roman",Font.PLAIN,25);

        // sizes
        //Dimension titleSize = new Dimension(2000,250);

        // create Swing components
        JLabel enterTextLbl = new JLabel("ENTER TEXT TO BE DECODED", SwingConstants.CENTER);
        enterTextLbl.setFont(titleFont);

        JLabel decodedMsgLbl = new JLabel("DECODED MESSAGE");
        decodedMsgLbl.setFont(lblFont);

        JButton decode = new JButton("DECODE");
        decode.setFont(btnFont);
        decode.setPreferredSize(new Dimension(150, 40));

        JButton clearBtn = new JButton("CLEAR");
        clearBtn.setFont(btnFont);
        clearBtn.setPreferredSize(new Dimension(150, 40));

        JButton copy = new JButton("COPY");
        copy.setFont(btnFont);
        copy.setPreferredSize(new Dimension(150, 40));

        JButton homeBtn = new JButton("HOME");
        homeBtn.setFont(btnFont);
        homeBtn.setPreferredSize(new Dimension(150, 40));

        JButton encodeBtn = new JButton("ENCODE");
        encodeBtn.setFont(btnFont);
        encodeBtn.setPreferredSize(new Dimension(150, 40));

        // decode textarea and scrollpane
        // set max number of characters
        final int maxNumberOfCharacters = 500;
        JTextArea inputMessage = new JTextArea(new DefaultStyledDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if ((getLength() + str.length()) <= maxNumberOfCharacters) {
                    super.insertString(offs, str, a);
                }
                else {
                    Toolkit.getDefaultToolkit().beep();
                    revalidate();
                }
            }
        });
        inputMessage.setWrapStyleWord(true);
        inputMessage.setLineWrap(true);
        inputMessage.setText(inputDefault);
        inputMessage.grabFocus();

        JScrollPane inputScrollPane = new JScrollPane(inputMessage);
        Dimension inputScrollSize = new Dimension(500, 25);
        inputScrollPane.setMinimumSize(inputScrollSize);
        inputScrollPane.setPreferredSize(inputScrollSize);
        inputScrollPane.setMaximumSize(inputScrollSize);

        // decoded message result textare and scroll pane
        JTextArea decodedMessage = new JTextArea();
        decodedMessage.setEditable(false);
        decodedMessage.setWrapStyleWord(true);
        decodedMessage.setLineWrap(true);
        decodedMessage.setForeground(Color.RED);
        decodedMessage.setText(decodedMsgDefault);

        JScrollPane decodedScrollPane = new JScrollPane(decodedMessage);
        Dimension decodedScrollSize = new Dimension(500, 25);
        decodedScrollPane.setMinimumSize(decodedScrollSize);
        decodedScrollPane.setPreferredSize(decodedScrollSize);
        decodedScrollPane.setMaximumSize(decodedScrollSize);

        // encode key label and spinner
        JLabel keyLbl = new JLabel("KEY:");
        keyLbl.setFont(lblFont);

        SpinnerModel key = new SpinnerNumberModel(1, 1, 26, 1);
        JSpinner keySpinner = new JSpinner(key);
        keySpinner.setFont(btnFont);
        keySpinner.setAlignmentX(RIGHT_ALIGNMENT);

        // add Swing components to the gui
        //// Labels /////////////////////////////////////////
        gc.ipadx = 100;
        gc.ipady = 100;

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(enterTextLbl, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(decodedMsgLbl, gc);

        //gc.insets = new Insets(0,0,0,0);

        //// TextBoxes /////////////////////////////////////////
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.PAGE_START;
        gc.weighty = 0.5;

        gc.gridx = 1;
        gc.gridy = 1;
        add(inputScrollPane, gc);

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(decodedScrollPane, gc);

        gc.fill = GridBagConstraints.NONE;

        //// Buttons ///////////////////////////////////////
        gc.ipadx = 100;
        gc.ipady = 50;
        gc.weighty = 0.5;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 1;

        // Decode and clear buttons and key spinners
        JPanel decodePanel = new JPanel();
        decodePanel.add(keyLbl);
        decodePanel.add(keySpinner);
        decodePanel.add(Box.createRigidArea(new Dimension(200,0)));
        decodePanel.add(decode);
        decodePanel.add(clearBtn);
        add(decodePanel, gc);

        gc.weighty = 0;
        gc.ipadx = 0;
        gc.ipady = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 2;
        add(copy, gc);

        gc.insets = new Insets(0,0,100,0);
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(homeBtn, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_END;
        add(encodeBtn, gc);

        // Add behaviors
        // Decode button pressed
        decode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Encode Text and put the encoded string in the decode textbox
                String decodedMsg = Caeser.decryptCipher(inputMessage.getText(), (int)key.getValue());
                decodedMessage.setText(decodedMsg);

                // Reset copy button to default color and text
                copy.setOpaque(false);
                copy.setText("COPY");
            }
        });

        // Clear button pressed
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text in the input message box
                inputMessage.setText("");
            }
        });

        // Copy button pressed
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // copy the text to clipboard
                StringSelection stringSelection = new StringSelection(decodedMessage.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Change color of button to green and text to "Copied!"
                copy.setBackground(Color.green);
                copy.setOpaque(true);
                copy.setText("COPIED!");
            }
        });

        // Home button pressed
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create welcome Screen
                JFrame welcomeFrame = new WelcomeFrame("Simple Text Encoder/Decoder");
                welcomeFrame.setResizable(false);
                welcomeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                welcomeFrame.setVisible(true);

                // Close encode screen
                setVisible(false);
                dispose();
            }
        });

        // Encode button pressed
        encodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create decode screen
                JFrame encodeFrame = new DecodeFrame("Encode");
                encodeFrame.setResizable(false);
                encodeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                encodeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                encodeFrame.setVisible(true);

                // Close encode screen
                setVisible(false);
                dispose();
            }
        });
    }
}

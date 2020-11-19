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

class EncodeFrame extends JFrame {
    EncodeFrame(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        final String inputDefault = "<Input Text>";
        final String encodedMsgDefault = "<Encoded Message Result>";

        // fonts
        Font btnFont = new Font("Times New Roman", Font.PLAIN, (int)Main.width/70);//20
        Font lblFont = new Font("Times New Roman",Font.PLAIN,(int)Main.width/64);//25

        //Font titleFont = new Font("Times New Roman", Font.PLAIN, (int)Main.width/18);//100

        // sizes
        //Dimension titleSize = new Dimension(2000,250);

        // create Swing components
        JLabel enterTextLbl = new JLabel("ENTER TEXT TO BE ENCODED");//, SwingConstants.CENTER);
        enterTextLbl.setFont(lblFont);

        JLabel encodedMsgLbl = new JLabel("ENCODED MESSAGE");
        encodedMsgLbl.setFont(lblFont);

        JButton encode = new JButton("ENCODE");
        encode.setFont(btnFont);
        encode.setPreferredSize(new Dimension((int)Main.width/11, (int)Main.width/42));//150 40

        JButton clearBtn = new JButton("CLEAR");
        clearBtn.setFont(btnFont);
        clearBtn.setPreferredSize(new Dimension((int)Main.width/11, (int)Main.width/42));//150 40

        JButton copy = new JButton("COPY");
        copy.setFont(btnFont);
        copy.setPreferredSize(new Dimension((int)Main.width/11, (int)Main.width/42));//150 40

        JButton homeBtn = new JButton("HOME");
        homeBtn.setFont(btnFont);
        homeBtn.setPreferredSize(new Dimension((int)Main.width/11, (int)Main.width/42));//150 40

        JButton decodeBtn = new JButton("DECODE");
        decodeBtn.setFont(btnFont);
        decodeBtn.setPreferredSize(new Dimension((int)Main.width/11, (int)Main.width/42));//150 40

        // encode textarea and scrollpane
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
        Dimension inputScrollSize = new Dimension((int)Main.width/4, (int)Main.width/64);//500 25
        inputScrollPane.setMinimumSize(inputScrollSize);
        inputScrollPane.setPreferredSize(inputScrollSize);
        inputScrollPane.setMaximumSize(inputScrollSize);

        // encoded message result textare and scroll pane
        JTextArea encodedMessage = new JTextArea();
        encodedMessage.setEditable(false);
        encodedMessage.setWrapStyleWord(true);
        encodedMessage.setLineWrap(true);
        encodedMessage.setForeground(Color.RED);
        encodedMessage.setText(encodedMsgDefault);

        JScrollPane encodedScrollPane = new JScrollPane(encodedMessage);
        Dimension encodedScrollSize = new Dimension((int)Main.width/4, (int)Main.width/64);//500 25
        encodedScrollPane.setMinimumSize(encodedScrollSize);
        encodedScrollPane.setPreferredSize(encodedScrollSize);
        encodedScrollPane.setMaximumSize(encodedScrollSize);

        // encode key label and spinner
        JLabel keyLbl = new JLabel("KEY:");
        keyLbl.setFont(lblFont);

        SpinnerModel key = new SpinnerNumberModel(1, 1, 26, 1);
        JSpinner keySpinner = new JSpinner(key);
        keySpinner.setFont(btnFont);
        keySpinner.setAlignmentX(RIGHT_ALIGNMENT);

        // add Swing components to the gui
        //// Labels /////////////////////////////////////////
        gc.ipadx = (int)Main.width/18;//100
        gc.ipady = (int)Main.width/18;//100

        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 0;
        add(enterTextLbl, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 1;
        add(encodedMsgLbl, gc);

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
        add(encodedScrollPane, gc);

        gc.fill = GridBagConstraints.NONE;

        //// Buttons ///////////////////////////////////////
        gc.ipadx = (int)Main.width/18;//100
        gc.ipady = (int)Main.width/32;//50
        gc.weighty = 0.5;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 1;

        // Encode and clear buttons and key spinners
        JPanel encodePanel = new JPanel();
        encodePanel.add(keyLbl);
        encodePanel.add(keySpinner);
        encodePanel.add(Box.createRigidArea(new Dimension((int)Main.width/9,0)));//200 0
        encodePanel.add(encode);
        encodePanel.add(clearBtn);
        add(encodePanel, gc);

        gc.weighty = 0;
        gc.ipadx = 0;
        gc.ipady = 0;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 2;
        add(copy, gc);

        gc.insets = new Insets(0,0,(int)Main.width/18,0);//0 0 100 0
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 1;
        gc.gridy = 2;
        add(homeBtn, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_END;
        add(decodeBtn, gc);

        // Add behaviors
        // Encode button pressed
        encode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Encode Text and put the encoded string in the decode textbox
                String encodedMsg = Caeser.encrypt(inputMessage.getText(), (int)key.getValue());
                encodedMessage.setText(encodedMsg);

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
                StringSelection stringSelection = new StringSelection(encodedMessage.getText());
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

        // Decode button pressed
        decodeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create decode screen
                JFrame decodeFrame = new DecodeFrame("Decode");
                decodeFrame.setResizable(false);
                decodeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                decodeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                decodeFrame.setVisible(true);

                // Close encode screen
                setVisible(false);
                dispose();
            }
        });
    }
}

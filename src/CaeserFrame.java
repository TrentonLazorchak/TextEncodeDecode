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

class CaeserFrame extends JFrame {
    CaeserFrame(String title) {
        super(title);

        // set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        final String inputDefault = "<Enter Text to Encode>";
        final String inputDecodeDefault = "<Enter Text to Decode>";
        final String encodedMsgDefault = "<Encoded Message>";
        final String decodedMsgDefault = "<Decoded Message>";

        // fonts
        Font titleFont = new Font("Times New Roman",Font.PLAIN,50);
        Font btnFont = new Font("Times New Roman", Font.PLAIN, 25);
        Font creditFont = new Font("Times New Roman", Font.PLAIN, 12);

        // sizes
        //Dimension titleSize = new Dimension(2000,250);

        // create Swing components
        JLabel enterTextLbl = new JLabel("Enter Text to Encode");
        enterTextLbl.setFont(titleFont);

        JLabel enterTextDecodeLbl = new JLabel("Enter Text to Decode");
        enterTextDecodeLbl.setFont(titleFont);

        JButton encode = new JButton("Encode");
        encode.setFont(btnFont);

        JButton decode = new JButton("Decode");
        decode.setFont(btnFont);

        JButton copy = new JButton("Copy to Clipboard");
        copy.setFont(btnFont);

        JButton copyDecoded = new JButton("Copy to Clipboard");
        copyDecoded.setFont(btnFont);

        JLabel credit = new JLabel("Created by Trenton Lazorchak, Jake Howell, Jahlin Jean-Baptiste, and Olanrewaju Arbisala.");
        credit.setFont(creditFont);
        credit.setHorizontalAlignment(JLabel.CENTER);

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

        JScrollPane inputScrollPane = new JScrollPane(inputMessage);
        Dimension inputScrollSize = new Dimension(500, 25);
        inputScrollPane.setMinimumSize(inputScrollSize);
        inputScrollPane.setPreferredSize(inputScrollSize);
        inputScrollPane.setMaximumSize(inputScrollSize);

        // decode text area and scrollpane
        JTextArea decodeMessage = new JTextArea(new DefaultStyledDocument() {
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
        decodeMessage.setWrapStyleWord(true);
        decodeMessage.setLineWrap(true);
        decodeMessage.setText(inputDecodeDefault);

        JScrollPane decodeScrollPane = new JScrollPane(decodeMessage);
        Dimension decodeScrollSize = new Dimension(500, 25);
        decodeScrollPane.setMinimumSize(decodeScrollSize);
        decodeScrollPane.setPreferredSize(decodeScrollSize);
        decodeScrollPane.setMaximumSize(decodeScrollSize);

        // encoded message result textare and scroll pane
        JTextArea encodedMessage = new JTextArea();
        encodedMessage.setEditable(false);
        encodedMessage.setWrapStyleWord(true);
        encodedMessage.setLineWrap(true);
        encodedMessage.setText(encodedMsgDefault);

        JScrollPane encodedScrollPane = new JScrollPane(encodedMessage);
        Dimension encodedScrollSize = new Dimension(500, 25);
        encodedScrollPane.setMinimumSize(encodedScrollSize);
        encodedScrollPane.setPreferredSize(encodedScrollSize);
        encodedScrollPane.setMaximumSize(encodedScrollSize);

        // decoded message result textarea and scroll pane
        JTextArea decodedMessage = new JTextArea();
        decodedMessage.setEditable(false);
        decodedMessage.setWrapStyleWord(true);
        decodedMessage.setLineWrap(true);
        decodedMessage.setText(decodedMsgDefault);

        JScrollPane decodedScrollPane = new JScrollPane(decodedMessage);
        Dimension decodedScrollSize = new Dimension(500, 25);
        decodedScrollPane.setMinimumSize(decodedScrollSize);
        decodedScrollPane.setPreferredSize(decodedScrollSize);
        decodedScrollPane.setMaximumSize(decodedScrollSize);

        // encode key label and spinner
        JLabel keyLbl = new JLabel("Key:");
        keyLbl.setFont(btnFont);

        SpinnerModel key = new SpinnerNumberModel(1, 1, 2147483647, 1);
        JSpinner keySpinner = new JSpinner(key);
        keySpinner.setFont(btnFont);

        // decode key label and spinner
        JLabel decodeKeyLbl = new JLabel("Key:");
        decodeKeyLbl.setFont(btnFont);

        SpinnerModel decodeKey = new SpinnerNumberModel(1, 1, 2147483647, 1);
        JSpinner decodeKeySpinner = new JSpinner(decodeKey);
        decodeKeySpinner.setFont(btnFont);

        // add Swing components to the gui
        //// Title /////////////////////////////////////////
        gc.insets = new Insets(0,0,50,0);
        gc.fill = GridBagConstraints.VERTICAL;
        gc.ipadx = 100;
        gc.ipady = 100;

        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 0;
        gc.gridy = 0;
        add(enterTextLbl, gc);

        gc.gridx = 3;
        gc.gridy = 0;
        add(enterTextDecodeLbl, gc);

        gc.insets = new Insets(0,0,0,0);

        //// TextBoxes /////////////////////////////////////////
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.PAGE_START;
        gc.weighty = 0.5;

        gc.gridx = 0;
        gc.gridy = 1;
        add(inputScrollPane, gc);

        gc.gridx = 3;
        gc.gridy = 1;
        add(decodeScrollPane, gc);

        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        add(encodedScrollPane, gc);

        gc.gridx = 3;
        gc.gridy = 1;
        add(decodedScrollPane, gc);

        gc.fill = GridBagConstraints.NONE;

        //// Buttons ///////////////////////////////////////
        gc.ipadx = 100;
        gc.ipady = 50;
        gc.weighty = 0.5;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy = 1;
        add(encode, gc);

        gc.gridx = 3;
        gc.gridy = 1;
        add(decode, gc);

        gc.weighty = 0.5;
        gc.ipadx = 50;
        gc.ipady = 25;
        gc.anchor = GridBagConstraints.PAGE_START;
        gc.gridx = 0;
        gc.gridy = 2;
        add(copy, gc);

        gc.gridx = 3;
        gc.gridy = 2;
        add(copyDecoded, gc);

        //// Spinner Models ///////////////////////////////////////
        gc.ipadx = 0;
        gc.ipady = 25;
        gc.weighty = 0;
        //gc.insets = new Insets(0,0,100,0);

        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.gridx = 0;
        gc.gridy = 0;
        add(keyLbl, gc);

        gc.gridx = 3;
        gc.gridy = 0;
        add(decodeKeyLbl, gc);

        gc.anchor = GridBagConstraints.LAST_LINE_END;
        gc.gridx = 0;
        gc.gridy = 0;
        add(keySpinner, gc);

        gc.gridx = 3;
        gc.gridy = 0;
        add(decodeKeySpinner, gc);

        //// Credits ////////////////////////////////////////
        gc.ipadx = 0;
        gc.ipady = 50;
        gc.weighty = 0;

        gc.anchor = GridBagConstraints.PAGE_END;
        gc.gridx = 1;
        gc.gridy = 3;
        add(credit, gc);

        // Add behaviors
        // Encode button pressed
        encode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Encode Text and put the encoded string in the decode textbox
                String encodedMsg = Caeser.encrypt(inputMessage.getText(), (int)key.getValue());
                encodedMessage.setText(encodedMsg);
            }
        });

        // Decode button pressed
        decode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Encode Text and put the encoded string in the decode textbox
                String decodedMsg = Caeser.decryptCipher(decodeMessage.getText(), (int)key.getValue());
                decodedMessage.setText(decodedMsg);
            }
        });

        // Copy to clipboard button pressed
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // copy the text to clipboard
                StringSelection stringSelection = new StringSelection(encodedMessage.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // TODO: display a successfully copied message and replace button

                // TODO: on encode press remove msg and put cop button back
            }
        });

        // Copy to clipboard button pressed for decode
        copyDecoded.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // copy the text to clipboard
                StringSelection stringSelection = new StringSelection(decodedMessage.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // TODO: display a successfully copied message and replace button

                // TODO: on encode press remove msg and put cop button back
            }
        });
    }
}

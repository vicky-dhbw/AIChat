package org.chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChatUI {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JTextField nameField;
    private JButton sendButton;

    public ChatUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("AI Chat" + " (client-id: " + Configuration.INSTANCE.clientId + ")");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BoxLayout(sendPanel, BoxLayout.Y_AXIS));
        sendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textField = new JTextField(20);
        Dimension preferredSize = textField.getPreferredSize();
        preferredSize.height += 20;
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredSize.height));
        textField.setPreferredSize(preferredSize);
        textField.setBorder(new EmptyBorder(6, 6, 6, 6));

        nameField = new JTextField(20);
        nameField.setBorder(new EmptyBorder(6, 6, 6, 6));
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBorder(new EmptyBorder(3, 0, 3, 3));
        Dimension textDimension = new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height);
        nameField.setMaximumSize(textDimension);

        sendButton = new JButton("Send");
        JLabel textLabel = new JLabel("Write a message!");
        textLabel.setBorder(new EmptyBorder(3, 0, 3, 3));

        sendPanel.add(nameLabel);
        sendPanel.add(nameField);
        sendPanel.add(Box.createVerticalStrut(5));
        sendPanel.add(textLabel);
        sendPanel.add(textField);
        sendPanel.add(Box.createVerticalStrut(5));
        sendPanel.add(sendButton);
        sendPanel.add(Box.createVerticalGlue());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel receivePanel = new JPanel(new BorderLayout());
        receivePanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(sendPanel, BorderLayout.WEST);
        frame.add(receivePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JFrame getFrame() {
        return frame;
    }
}

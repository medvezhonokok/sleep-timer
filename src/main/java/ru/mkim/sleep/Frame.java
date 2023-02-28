package ru.mkim.sleep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 03.11.2022, четверг
 **/
public class Frame extends JFrame implements ActionListener {
    private static JTextField textField;
    private static JButton[] buttons;

    public Frame(String title) throws HeadlessException {
        super(title);
        initFields();
        getContentPane().setBackground(Color.gray);
        setLayout(null);
        setSize(460, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initFields() {
        initTextField();
        initButtons();
    }

    private void initButtons() {
        buttons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            buttons[i] = new JButton((i + 1) * 100 + "");
            buttons[i].setBounds((i + 1) * 50 + i * 50, 100, 75, 75);
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }
    }

    private void initTextField() {
        textField = new JTextField("Enter count of min-s to sleep: ", 20);
        textField.setBounds(10, 10, 430, 60);
        textField.addActionListener(this);
        add(textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textField) {
            boolean resultSuccess = RunScript.run(textField.getText().replaceAll("Enter count of min-s to sleep: ", ""), textField);
            if (resultSuccess) {
                closeFrame();
            }
        }

        for (int i = 0; i < 4; i++) {
            if (buttons[i] == e.getSource()) {
                boolean resultSuccess = RunScript.run(buttons[i].getText().replaceAll("Enter count of min-s to sleep: ", ""), textField);
                if (resultSuccess) {
                    closeFrame();
                }
            }
        }
    }

    private void closeFrame() {
        Timer timer = new Timer(2000, e1 -> dispose());
        timer.start();
    }
}

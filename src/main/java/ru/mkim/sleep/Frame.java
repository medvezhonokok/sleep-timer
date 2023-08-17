package ru.mkim.sleep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static java.nio.charset.StandardCharsets.*;

/**
 * Represents a graphical user interface frame.
 * <p>
 *
 * @author medvezhonokok
 */
public class Frame extends JFrame implements ActionListener {

    /**
     * The width of the frame.
     */
    public static final int WIDTH = 500;

    /**
     * The height of the frame.
     */
    public static final int HEIGHT = 500;

    /**
     * The text field used for input.
     */
    private static final JTextField field = new JTextField();

    /**
     * Constructs a new Frame object with the given title.
     *
     * @param title the title of the frame
     * @throws HeadlessException if the GraphicsEnvironment is headless
     */
    public Frame(String title) throws HeadlessException {
        super(title);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        initField();
        add(field);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Initializes the text field and adds an ActionListener to it.
     */
    private void initField() {
        field.addActionListener(this);
        field.setBounds(10, HEIGHT / 2, getWidth() - 30, getHeight() / 4);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                field.setBounds(10, getWidth() / 2, getWidth() - 30, getHeight() / 4 - 10);
                field.setText(new String("Type some minutes there and then press 'Enter' ".getBytes(), UTF_8));
            }
        });
    }

    /**
     * Handles the actionPerformed event.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        final String[] args = e.getActionCommand().split("\\s+");
        try {
            final int countOfMinutesToShutdown = Integer.parseInt(args[args.length - 1]);
            if (countOfMinutesToShutdown <= 0) {
                field.setText("Please, type a positive count of minutes.");
            } else {
                Utils.runScript(countOfMinutesToShutdown, field);
            }
        } catch (Exception ignored) {
            field.setText("Usage: ['10'] or ['Type some minutes there and then press 'Enter' 123'].");
        }
    }

    /**
     * The main method that creates a new Frame object.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Frame("sleep-timer");
    }
}

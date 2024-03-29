package ru.mkim.sleep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalTime;
import java.util.regex.Pattern;

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
    private static final JTextField TEXT_FIELD = new JTextField();

    /**
     * Pattern to handle strings with white spaces.
     */
    private static final Pattern WHITE_SPACES_PATTERN = Pattern.compile("\\s+");

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
        add(TEXT_FIELD);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Initializes the text field and adds an ActionListener to it.
     */
    private void initField() {
        TEXT_FIELD.addActionListener(this);
        TEXT_FIELD.setBounds(10, HEIGHT / 2, getWidth() - 30, getHeight() / 4);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                TEXT_FIELD.setBounds(10, getWidth() / 2, getWidth() - 30, getHeight() / 4 - 10);
                TEXT_FIELD.setText(new String("Type some minutes there and then press 'Enter' ".getBytes(), UTF_8));
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
        String[] tokens = WHITE_SPACES_PATTERN.split(e.getActionCommand());

        try {
            int countOfMinutesToShutdown = Integer.parseInt(tokens[tokens.length - 1]);
            if (countOfMinutesToShutdown <= 0) {
                TEXT_FIELD.setText("Please, type a positive count of minutes.");
            } else {
                Utils.runScript(countOfMinutesToShutdown);
                TEXT_FIELD.setText(String.format("Shutdown in: %s.",
                        String.valueOf(LocalTime.now().plusMinutes(countOfMinutesToShutdown)).split("\\.")[0]));
                new Timer(2000, event -> this.dispose()).start();
            }
        } catch (NumberFormatException ignored) {
            TEXT_FIELD.setText("Usage: ['10'] or ['Type some minutes there and then press 'Enter' 123'].");
        } catch (RuntimeException ex) {
            TEXT_FIELD.setText("Unexpected error: " + ex.getMessage());
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

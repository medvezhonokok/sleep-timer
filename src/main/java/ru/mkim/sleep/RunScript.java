package ru.mkim.sleep;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 03.11.2022, четверг
 **/
public class RunScript {

    private static String PATH = System.getProperty("user.dir");

    private final static boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

    public static boolean run(final String countOfMinuter, final JTextField textField) {

        if (isWindows) {
            PATH += "\\scripts\\sleep.bat ";
        } else {
            PATH += "/scripts/sleep.sh ";
        }

        try {
            final int N = Integer.parseInt(countOfMinuter);

            final String command = PATH + N;
            final Process process;

            try {
                process = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                process.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            final LocalTime now = LocalTime.now();
            final String[] text = String.valueOf(now.plusMinutes(N)).split("\\.");

            textField.setText("Shutdown in: " + text[0]);
            return true;
        } catch (NumberFormatException | NullPointerException ignored) {
            textField.setText(String.format("Usage: \n\t%s\n\tor %s", "Enter counts of min-s to sleep: <N> [Salt].", "<N> [Salt]."));
            return false;
        }
    }
}

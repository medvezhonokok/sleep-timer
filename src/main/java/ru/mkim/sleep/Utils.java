package ru.mkim.sleep;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;

/**
 * A utility class that provides helper methods.
 * <p>
 *
 * @author medvezhonokok
 */
public final class Utils {

    /**
     * Path to directory, where stores all scripts.
     */
    private static Path scriptsDir = Path.of("").toAbsolutePath().resolve("scripts");

    /**
     * Boolean variable to know which script need to run.
     */
    private static final boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    private Utils() {
    }

    /**
     * Runs a script to perform a shutdown after a specified number of minutes.
     *
     * @param minutesToShutdown the number of minutes until shutdown
     * @param field             the JTextField to display the shutdown time
     * @throws RuntimeException if an error occurs while running the script
     */
    @SuppressWarnings("deprecation")
    public static void runScript(int minutesToShutdown, JTextField field) {
        scriptsDir = isWindows ? scriptsDir.resolve("sleep.bat") : scriptsDir.resolve("sleep.sh");

        Process process;

        try {
            process = Runtime.getRuntime().exec(scriptsDir + " " + minutesToShutdown);
            process.waitFor();
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        field.setText(String.format("Shutdown in: %s",
                String.valueOf(LocalTime.now().plusMinutes(minutesToShutdown)).split("\\.")[0]));
    }
}
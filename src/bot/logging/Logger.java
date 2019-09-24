package bot.logging;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Logger {
    private static TextArea loggingArea;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void initialize(TextArea loggingArea) {
        Logger.loggingArea = loggingArea;
        loggingArea.setEditable(false);
        loggingArea.setWrapText(true);
    }

    public static void log(String message) {
        Platform.runLater(
                () -> {
                    loggingArea.setText(loggingArea.getText() + "[" + formatter.format(LocalDateTime.now()) + "] " + message + "\n");
                    loggingArea.selectPositionCaret(loggingArea.getLength());
                    loggingArea.deselect();
                }
        );
    }
}

package bot.logging;

import javafx.scene.control.TextArea;
import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Logger {
    private static TextArea loggingArea;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static void initialize(TextArea loggingArea){
        Logger.loggingArea = loggingArea;
    }
    public static void log(String message){
        loggingArea.setText(loggingArea.getText() + "[" + formatter.format(LocalDateTime.now()) + "] " + message + "\n");
    }
}

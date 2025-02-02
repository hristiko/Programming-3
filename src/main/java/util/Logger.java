package util;

import java.text.SimpleDateFormat;

public class Logger {
    public static final String RESET = "\u001b[0m";
    public static final String RED = "\u001b[31m";
    public static final String GREEN = "\u001b[32m";
    public static final String YELLOW = "\u001b[33m";
    public static final String BLUE = "\u001b[34m";
    public static final String MAGENTA = "\u001b[35m";
    public static final String CYAN = "\u001b[36m";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static void log(String message){
        log(message, LogLevel.Info);
    }
    public static void log(String message, LogLevel level){
        // [2024-01-01 10:30:24.657][threadName] Info: nas message
        String dateString = dateFormat.format(System.currentTimeMillis());
        String threadName = Thread.currentThread().getName();
        String finalMessage = "["+ dateString +"]["+ threadName +"] "+ level +": ";

        switch (level) {
            case Info: {
                finalMessage = YELLOW + finalMessage + RESET;
                break;
            }
            case Debug: {
                finalMessage = BLUE + finalMessage + RESET;
                break;
            }
            case Warn: {
                finalMessage = MAGENTA + finalMessage + RESET;
                break;
            }
            case Error: {
                finalMessage = RED + finalMessage + RESET;
                break;
            }
            case Success: {
                finalMessage = GREEN + finalMessage + RESET;
                break;
            }
            case Status: {
                finalMessage = CYAN + finalMessage + RESET;
                break;
            }
        }
        System.out.println(finalMessage + message);
    }
}
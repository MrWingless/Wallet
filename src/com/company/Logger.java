package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Logger {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
    private static final String LOG_FILE_DIRECTORY = "Logs";
    private static final String LOG_FILE_NAME_LOCATION = "Logs\\LogFile_";
    private static final Path directoryPath = Paths.get(LOG_FILE_DIRECTORY);
    private static Path logFilePath = Paths.get(LOG_FILE_NAME_LOCATION + SIMPLE_DATE_FORMAT.format(new Date()));

    public enum LogType {
        DATABASE,
        INFORMATIVE,
        LOGGER,
        MEMORY,
        WALLET_SERVER,
        JOB_SERVER
    }

    /**
     * Logs the text into the latest active log file.
     * Each log file can contain only 100 lines. After that a new one is created.
     */
    public static void log(LogType type, String text) {
        try {
            if (!Files.exists(logFilePath)) {
                createNewLogFile();
            }
            List<String> logFileLines = Files.readAllLines(logFilePath);
            if (logFileLines.size() > 98) {
                write(type, "Log file reached Max Size. Creating new log file...");
                createNewLogFile();
                logFileLines.add(write(type, text));
            } else {
                logFileLines.add(write(type, text));
            }
            Files.write(logFilePath, logFileLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Creates a new Log File and/or Directory the logs are kept in if they don't exist already
    private static void createNewLogFile() throws IOException {
        logFilePath = Paths.get(LOG_FILE_NAME_LOCATION + SIMPLE_DATE_FORMAT.format(new Date()));
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }
        if (!Files.exists(logFilePath)) {
            Files.createFile(logFilePath);
            log(LogType.LOGGER,"Created new Log File!");
        }
    }

    private static String write(LogType type, String text) {
        Date date = new Date();
        return "" + SIMPLE_DATE_FORMAT.format(date) + " " + type.toString() + " " + text;
    }
}

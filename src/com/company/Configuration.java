package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class Configuration {
    private static final Logger.LogType LOG_TYPE = Logger.LogType.CONFIGURATION;
    private static final String CONFIG_FILE_NAME_LOCATION = "Conf\\Config.txt";
    private static final Path CONF_FILE_PATH = Paths.get(CONFIG_FILE_NAME_LOCATION);
    private double balanceChangeLowerLimit = -1000000;
    private double balanceChangeUpperLimit = 1000000;

    public Configuration() {
        try {
            List<String> logFileLines = Files.readAllLines(CONF_FILE_PATH);

            for (String s : logFileLines) {
                if (s.contains("TxLowerLimit")) {
                    balanceChangeLowerLimit = readConfValue(s);
                }
                if (s.contains("TxUpperLimit")) {
                    balanceChangeUpperLimit = readConfValue(s);
                }
                if (s.contains("DebugMode")) {
                    if (readConfValue(s) == 1) {
                        Logger.setDebugMode(true);
                    }
                }
            }
        } catch (IOException e) {
            Logger.log(Logger.LogType.ERROR, "Configuration File Missing!");
        }

        Logger.log(Logger.LogType.INFORMATIVE, "Balance Change Lower Limit = " + balanceChangeLowerLimit);
        Logger.log(Logger.LogType.INFORMATIVE, "Balance Change Upper Limit = " + balanceChangeUpperLimit);
    }

    public double getBalanceChangeLowerLimit() {
        return balanceChangeLowerLimit;
    }

    public double getBalanceChangeUpperLimit() {
        return balanceChangeUpperLimit;
    }

    private double readConfValue(String lineInFile) {
        double answer = 0;
        if (!lineInFile.contains("=")) {
            Logger.log(Logger.LogType.ERROR, "Configuration Line does not Contain \'=\' : \"" + lineInFile + "\"! Check : " + CONFIG_FILE_NAME_LOCATION);
        } else {
            String[] configurationValues = lineInFile.split("=");
            if (configurationValues.length < 2 || configurationValues.length > 2) {
                Logger.log(Logger.LogType.ERROR, "Configuration : \"" + configurationValues[0] + "\" : is invalid! Check : " + CONFIG_FILE_NAME_LOCATION);
            } else {
                Logger.log(LOG_TYPE, "Value Read from Conf File : " + configurationValues[0] + " : set as : " + configurationValues[1]);
                answer = Double.parseDouble(configurationValues[1]);
            }
        }
        return answer;
    }
}

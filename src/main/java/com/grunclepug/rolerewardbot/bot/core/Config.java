package com.grunclepug.rolerewardbot.bot.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;

/**
 * Reads the config file (config.txt)
 * @author GrunclePug
 */
public class Config {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm");
    private static final String FILE_NAME = "src/main/resources/config.txt";
    private static String token;
    private static String prefix;

    private Config() {}

    /**
     * Method to read the file
     */
    public static void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            token = br.readLine().substring(6);
            prefix = br.readLine().substring(7);
            br.close();
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Accessor for token
     * @return The bot token
     */
    public static String getToken() {
        return token;
    }

    /**
     * Accessor for prefix
     * @return The bot prefix
     */
    public static String getPrefix() {
        return prefix;
    }
}

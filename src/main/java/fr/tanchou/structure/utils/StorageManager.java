package fr.tanchou.structure.utils;

import java.io.*;

public class StorageManager {
    private static final String ERR_MSG = "StorageManager, An error occurred while : ";

    public static void writeToFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println(ERR_MSG + "writing to file: " + e.getMessage());
        }
    }

    public static String readFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println(ERR_MSG + "reading from file: " + e.getMessage());
        }
        return content.toString();
    }
}
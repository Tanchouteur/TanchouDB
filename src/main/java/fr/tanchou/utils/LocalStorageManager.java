package fr.tanchou.utils;

import fr.tanchou.dataInstance.Tuple;
import fr.tanchou.structure.DataJson;
import fr.tanchou.structure.Table;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class LocalStorageManager implements StorageManager {
    private final String ERR_MSG = "LocalStorageManager, An error occurred while : ";
    private String basePath = "TanchouDB/";
    private String extension = ".json";

    private static LocalStorageManager instance;

    public static LocalStorageManager getInstance() {
        if (instance == null) {
            instance = new LocalStorageManager();
        }
        return instance;
    }

    private LocalStorageManager() {
        // If the directory does not exist, create it
        File directory = new File(getBasePath());
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Directory created: " + getBasePath());
            } else {
                System.err.println(ERR_MSG + "creating directory");
            }
        }
    }

    LocalStorageManager(String basePath, String extension) {
        if (basePath == null){
            throw new IllegalArgumentException("basePath cannot be null");
        }else if (extension == null){
            throw new IllegalArgumentException("extension cannot be null");
        }
        this.setBasePath(basePath);
        this.setExtension(extension);
    }

    @Override
    public void writeToFile(String filename, String content) {

        filename = getBasePath() + filename + getExtension();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println(ERR_MSG + "writing to file: " + e.getMessage());
        }
    }

    @Override
    public String readFromFile(String filename) {

        if (filename == null){
            throw new IllegalArgumentException("filename cannot be null");
        }else if (filename.isEmpty()){
            throw new IllegalArgumentException("filename cannot be empty");
        }

        filename = getBasePath() + filename + getExtension();

        if (!new File(filename).exists()) { //if file does not exist

            String location = "";

            for (String part : filename.split("/")) {
                location += part + "/";
                if (part.contains(".")) {//if part contains extension create file on disk
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                        writer.write("");
                    } catch (IOException e) {
                        System.err.println(ERR_MSG + "writing to file: " + e.getMessage());
                    }
                } else {
                    File directory = new File(location);
                    if (!directory.exists()) {
                        if (directory.mkdir()) {
                            System.out.println("Directory created: " + location);
                        } else {
                            System.err.println(ERR_MSG + "creating directory");
                        }
                    }
                }
            }
        }

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                //create Directory
                File directory = new File(getBasePath());
                if (!directory.exists()) {
                    if (directory.mkdir()) {
                        System.out.println("Directory created: " + getBasePath());
                    } else {
                        System.err.println(ERR_MSG + "creating directory");
                    }
                }
            }else {
                System.err.println(ERR_MSG + "reading from file: " + e.getMessage());
            }
        }
        return content.toString();
    }

    @Override
    public void deleteFile(String name) {
        name = getBasePath() + name + getExtension();
        File file = new File(name);
        if (!file.delete()) {
            System.err.println(ERR_MSG + "deleting file");
        }
    }

    @Override
    public Set<Tuple> loadTableData(String dbName, String schemaName, Table table) {
        String filename = "data/" + dbName + "/" + schemaName + "/" + table.getName();
        String content = readFromFile(filename);

        Set<Tuple> data = new HashSet<>();

        while (content.contains("[")) {
            int start = content.indexOf("[");
            int end = content.indexOf("]");
            DataJson dataJson = JSONParser.getInstance().parseData(content.substring(start, end + 1), table);
            data.add(new Tuple((String) dataJson.getData().get(table.getColumnNamePk()),dataJson));
            content = content.substring(end + 1);
        }

        return data;
    }

    private void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    private void setExtension(String extension) {
        this.extension = extension;
    }

    private String getBasePath() {
        return basePath;
    }

    private String getExtension() {
        return extension;
    }
}
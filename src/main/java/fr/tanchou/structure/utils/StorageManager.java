package fr.tanchou.structure.utils;

public interface StorageManager {

    void writeToFile(String filename, String content);

    String readFromFile(String filename);

    void deleteFile(String name);
}

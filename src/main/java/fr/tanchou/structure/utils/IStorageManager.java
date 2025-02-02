package fr.tanchou.structure.utils;

public interface IStorageManager {

    void writeToFile(String filename, String content);

    String readFromFile(String filename);

}

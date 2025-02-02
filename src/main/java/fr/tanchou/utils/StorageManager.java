package fr.tanchou.utils;

import fr.tanchou.dataInstance.Tuple;
import fr.tanchou.structure.DataJson;
import fr.tanchou.structure.Table;

import java.util.Set;

public interface StorageManager {

    void writeToFile(String filename, String content);

    String readFromFile(String filename);

    void deleteFile(String name);

    Set<Tuple> loadTableData(String dbName, String schemaName, Table table);
}

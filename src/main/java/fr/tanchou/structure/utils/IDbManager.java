package fr.tanchou.structure.utils;

import fr.tanchou.structure.Database;

import java.util.Map;

public interface IDbManager {
    void createDatabase(String dbName) throws IllegalArgumentException;
    void removeDatabase(String name) throws IllegalArgumentException;
    void listDatabases();
    Map<String, Database> getDatabasesMap();
}

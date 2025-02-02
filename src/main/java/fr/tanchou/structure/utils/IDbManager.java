package fr.tanchou.structure.utils;

import fr.tanchou.structure.Database;
import fr.tanchou.structure.DbNameList;

import java.util.Map;

public interface IDbManager {
    void createDatabase(String dbName) throws IllegalArgumentException;
    void removeDatabase(String name) throws IllegalArgumentException;
    void listDatabases();
    void setDbNameList(DbNameList dbNameList);
    DbNameList getDbNameList();
    Map<String,Database> getDatabasesMap();
    void commit();
}

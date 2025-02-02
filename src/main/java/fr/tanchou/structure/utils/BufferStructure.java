package fr.tanchou.structure.utils;

import fr.tanchou.structure.Database;
import fr.tanchou.structure.DbNameList;

import java.util.Map;

public interface BufferStructure {

    void addDatabase(Database db);

    void delete(String name) throws IllegalArgumentException;

    Map<String, Database> getDatabasesMap();

    DbNameList getDbNameList();
}

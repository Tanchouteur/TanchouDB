package fr.tanchou.structure.utils;

import fr.tanchou.structure.Database;
import fr.tanchou.structure.DbNameList;

public interface Serializer<T> {
    T serializeDatabase(Database database);
    T serializeDBList(DbNameList dbNameList);
}

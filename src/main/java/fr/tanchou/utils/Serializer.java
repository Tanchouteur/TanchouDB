package fr.tanchou.utils;

import fr.tanchou.structure.Database;
import fr.tanchou.structure.DbNameList;

import java.util.Map;

public interface Serializer<T> {

    T serializeDatabase(Database database);
    T serializeDBList(DbNameList dbNameList);
    T serializeData(Map<String, Object> datas);

}

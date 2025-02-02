package fr.tanchou.dataInstance;

import fr.tanchou.structure.Table;

public interface BufferData {

    boolean addData(String dbName, String tableName, Tuple tuple);
    boolean deleteData(String dbName, String tableName, Tuple tuple);

    Object getAutoIncrement(String name);
}

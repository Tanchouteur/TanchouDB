package fr.tanchou.dataInstance;

import java.util.Set;

public interface BufferData {

    static BufferData getInstance() {
        return null;
    }

    boolean addData(String dbName, String tableName, Tuple tuple);
    boolean deleteData(String dbName, String tableName, Tuple tuple);
    Set<Tuple> getData(String dbName, String tableName);

    Object getAutoIncrement(String name);
}

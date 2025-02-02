package fr.tanchou.dataInstance;

import fr.tanchou.structure.*;
import fr.tanchou.structure.utils.FullBufferStructure;
import fr.tanchou.utils.LocalStorageManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FullBufferData implements BufferData {
    private final Map<String, Map<String, Set<Tuple>>> tablesIndex;
    private final Map<String, Increment> incrementIndex;

    private static FullBufferData instance;

    public static FullBufferData getInstance() {
        if (instance == null) {
            instance = new FullBufferData(FullBufferStructure.getInstance().getDatabasesMap());
        }

        return instance;
    }

    private FullBufferData(Map<String, Database> databaseMap) {
        this.tablesIndex = new HashMap<>(databaseMap.size()+5);
        this.incrementIndex = new HashMap<>(databaseMap.size()+5);

        for (Database db : databaseMap.values()) {

            Map<String, Set<Tuple>> tables = new HashMap<>(db.getSchemas().size()+3);

            for (ASchema schema : db.getSchemas().values()) {
                for (Table table : schema.getTables()) {

                    tables.put(table.getName(), LocalStorageManager.getInstance().loadTableData(db.getName(), schema.getName(), table));

                }
            }
            tablesIndex.put(db.getName(), tables);
        }
    }

    @Override
    public boolean addData(String dbName, String tableName, Tuple tuple) {

        if (tableName != null && tuple != null && tablesIndex.containsKey(dbName) && tablesIndex.get(dbName).containsKey(tableName)) {
            tablesIndex.get(dbName).get(tableName).add(tuple);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteData(String dbName, String tableName, Tuple tuple) {
        return false;
    }

    @Override
    public Increment getAutoIncrement(String tableName) {
        return getIncrementIndex().get(tableName);
    }

    public Map<String, Map<String, Set<Tuple>>> getTablesIndex() {
        return tablesIndex;
    }

    public Map<String, Increment> getIncrementIndex() {
        return incrementIndex;
    }
}

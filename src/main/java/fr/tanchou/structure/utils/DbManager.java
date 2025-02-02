package fr.tanchou.structure.utils;

import fr.tanchou.dataInstance.FullBufferData;
import fr.tanchou.dataInstance.Tuple;
import fr.tanchou.enums.KeyType;
import fr.tanchou.structure.*;

import java.util.HashMap;
import java.util.Map;

public class DbManager implements IDbManager {

    private final BufferStructure bufferStructure;

    public DbManager() {
        this.bufferStructure = FullBufferStructure.getInstance();
    }

    @Override
    public void createDatabase(String dbName) {
        this.addDatabase(DDLCall.createDatabase(dbName));

        for (ASchema schema : this.getDatabasesMap().get(dbName).getSchemas().values()) {
            for (Table table : schema.getTables()) {

                Increment increment = FullBufferData.getInstance().getAutoIncrement(table.getName());

                for (Column column : table.getColumns()) {
                    if (column.isPrimaryKey()){

                        Map<String, Object> map = new HashMap<>();

                        if (table.getColumns().get(0).isAutoIncrement()){
                            map.put(table.getColumns().get(0).getName(), increment.getIncrement());
                            increment.increment();
                        }else {
                            map.put(table.getColumns().get(1).getName(), table.getName());
                            map.put(table.getColumns().get(2).getName(), column.getName());
                        }

                        FullBufferData.getInstance().addData(dbName, table.getName(), new Tuple(column.getName(), new DataJson(map)));
                    }
                }
            }

        }
    }

    private void addDatabase(Database db) throws IllegalArgumentException {
        this.getBufferStructure().addDatabase(db);
    }

    @Override
    public void removeDatabase(String name) throws IllegalArgumentException {
        this.getBufferStructure().delete(name);
    }

    @Override
    public void listDatabases() {
        System.out.println(this.getBufferStructure().getDbNameList());
    }

    private BufferStructure getBufferStructure() {
        return this.bufferStructure;
    }

    @Override
    public Map<String, Database> getDatabasesMap() {
        return this.getBufferStructure().getDatabasesMap();
    }
}

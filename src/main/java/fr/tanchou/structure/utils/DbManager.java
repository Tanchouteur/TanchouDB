package fr.tanchou.structure.utils;

import fr.tanchou.structure.*;

import java.util.HashMap;
import java.util.Map;

public class DbManager implements IDbManager {

    private final BufferStructure bufferStructure;

    public DbManager() {
        this.bufferStructure = new FullBufferStructure();
    }

    @Override
    public void createDatabase(String dbName) {
        this.addDatabase(DDLCall.createDatabase(dbName));
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

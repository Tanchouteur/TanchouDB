package fr.tanchou.structure.utils;

import fr.tanchou.structure.Database;
import fr.tanchou.structure.DbNameList;
import fr.tanchou.utils.JSONParser;
import fr.tanchou.utils.LocalStorageManager;
import fr.tanchou.utils.Parser;
import fr.tanchou.utils.StorageManager;

import java.util.HashMap;
import java.util.Map;

public class FullBufferStructure implements BufferStructure {

    private final DbNameList dbNameList;
    private final Map<String, Database> databaseMap;
    private final StorageManager storageManager;
    private final Parser<String> parser = JSONParser.getInstance();

    private boolean isDirty = false;

    private static FullBufferStructure instance;

    private FullBufferStructure() {
        this.storageManager = LocalStorageManager.getInstance();
        this.dbNameList = loadDBList();
        this.databaseMap = loadDatabases();

    }

    public static FullBufferStructure getInstance() {
        if (instance == null) {
            instance = new FullBufferStructure();
        }

        return instance;
    }

    @Override
    public void addDatabase(Database db) {
        if (this.getDatabasesMap().containsKey(db.getName())) {
            throw new IllegalArgumentException("Database already exists");
        }

        this.getDbNameList().addDatabase(db.getName());
        this.getDatabasesMap().put(db.getName(), db);

        this.setDirty(true);
        commit();
    }

    @Override
    public void delete(String name) throws IllegalArgumentException {

        if (!this.getDbNameList().getDatabases().contains(name)) {
            throw new IllegalArgumentException("Database does not exist");
        }

        this.getDbNameList().removeDatabase(name);
        this.getDatabasesMap().remove(name);

        this.setDirty(true);
    }

    private void writeToFile(String filename, String content) {
        this.getStorageManager().writeToFile(filename, content);
    }

    public String readFromFile(String filename) {
        if (this.isDirty()) {
            this.commit();
        }

        return this.getStorageManager().readFromFile(filename);
    }

    private Map<String, Database> loadDatabases() {
        Map<String, Database> databases = new HashMap<>(getDbNameList().length() + 2);

        for (String dbName : this.getDbNameList().getDatabases()) {
            databases.put(dbName, this.getParser().parseDatabase(this.readFromFile(dbName)));
        }

        return databases;
    }

    private DbNameList loadDBList() {
        return this.getParser().parseDBList(this.readFromFile("dbNameList"));
    }

    private void commit() {
        for (Database db : this.getDatabasesMap().values()) {
            if (db.isDirty()) {
                this.writeToFile(db.getName(), db.toJSONObject());
            }
        }

        if (this.getDbNameList().isDirty()) {
            this.writeToFile("dbNameList", this.getDbNameList().toJSONObject());
        }

        this.setDirty(false);
    }

    public DbNameList getDbNameList() {
        return this.dbNameList;
    }

    public Map<String,Database> getDatabasesMap() {
        return this.databaseMap;
    }

    public Database getDatabase(String dbName) {
        return this.getDatabasesMap().get(dbName);
    }

    private StorageManager getStorageManager() {
        return this.storageManager;
    }

    private Parser<String> getParser() {
        return this.parser;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }
}

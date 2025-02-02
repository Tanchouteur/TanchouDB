package fr.tanchou.structure.utils;

import fr.tanchou.structure.*;

import java.util.HashMap;
import java.util.Map;

public class DbManager implements IDbManager {

    private DbNameList dbNameList;
    private final Map<String,Database> databaseMap;
    private final StorageManager storageManager;
    private final Parser<String> parser = JSONParser.getInstance();

    public DbManager() {
        this.storageManager = LocalStorageManager.getInstance();
        this.setDbNameList(this.loadDBList());

        this.databaseMap = new HashMap<>(this.getDbNameList().length()+5);
        this.loadDatabases();
    }

    private void loadDatabases() {
        for (String dbName : this.getDbNameList().getDatabases()) {
            this.getDatabasesMap().put(dbName, this.getParser().parseDatabase(this.getStorageManager().readFromFile(dbName)));
        }
    }

    private DbNameList loadDBList() {
        return this.getParser().parseDBList(this.getStorageManager().readFromFile("dbNameList"));
    }

    public void createDatabase(String dbName) throws IllegalArgumentException {
        Schema userSchema = new UserSchema();
        Schema constraintSchema = new ConstraintSchema();

        Map<String, Schema> schemas = new HashMap<>(3);
        schemas.put("user", userSchema);
        schemas.put("constraint", constraintSchema);

        Database db = new Database(dbName, schemas);

        this.addDatabase(db);
    }

    private void addDatabase(Database db) throws IllegalArgumentException {
        if (this.getDatabasesMap().containsKey(db.getName())) {
            throw new IllegalArgumentException("Database already exists");
        }
        this.getDbNameList().addDatabase(db.getName());
        this.getDatabasesMap().put(db.getName(), db);
    }

    public void removeDatabase(String name) throws IllegalArgumentException {
        if (!this.getDbNameList().removeDatabase(name)){
            throw new IllegalArgumentException("Database does not exist");
        }
        this.getDatabasesMap().remove(name);
    }

    public void listDatabases() {
        System.out.println(this.getDbNameList());
    }

    public void setDbNameList(DbNameList dbNameList) {
        this.dbNameList = dbNameList;
    }

    public DbNameList getDbNameList() {
        return this.dbNameList;
    }

    public Map<String,Database> getDatabasesMap() {
        return this.databaseMap;
    }

    public void commit() {
        for (Database db : this.getDatabasesMap().values()) {
            if (db.isDirty()) {
                this.getStorageManager().writeToFile(db.getName(), db.toJSONObject());
            }
        }
        if (this.getDbNameList().isDirty()) {
            this.getStorageManager().writeToFile("dbNameList", this.getDbNameList().toJSONObject());
        }
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
}

package fr.tanchou.structure.utils;

import fr.tanchou.structure.DbNameList;
import fr.tanchou.structure.Database;

import java.util.HashMap;
import java.util.Map;

public class DbManager {

    private DbNameList dbNameList;
    private final Map<String,Database> databaseMap;

    public DbManager() {
        this.setDbNameList(this.loadDBList());

        this.databaseMap = new HashMap<>(this.getDbNameList().length()+5);
        this.loadDatabases();
    }

    private void loadDatabases() {
        for (String dbName : this.getDbNameList().getDatabases()) {
            this.getDatabasesMap().put(dbName, JSONParser.parseDatabase(StorageManager.readFromFile(dbName)));
        }
    }

    private DbNameList loadDBList() {
        return JSONParser.parseDBList(StorageManager.readFromFile("dbNameList"));
    }

    public void addDatabase(Database db) throws IllegalArgumentException {
        if (this.getDatabasesMap().containsKey(db.getName())) {
            throw new IllegalArgumentException("Database already exists");
        }
        this.getDbNameList().addDatabase(db.getName());
        this.getDatabasesMap().put(db.getName(), db);
    }

    public void removeDatabase(String name) {
        this.getDbNameList().removeDatabase(name);
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
                StorageManager.writeToFile(db.getName(), db.toJSONObject());
            }
        }
        if (this.getDbNameList().isDirty()) {
            StorageManager.writeToFile("dbNameList", this.getDbNameList().toJSONObject());
        }
    }

    public Database getDatabase(String db1) {
        return this.getDatabasesMap().get(db1);
    }
}

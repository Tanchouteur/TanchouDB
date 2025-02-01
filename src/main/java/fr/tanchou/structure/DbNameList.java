package fr.tanchou.structure;

import fr.tanchou.storage.JSONSerializer;

import java.util.ArrayList;
import java.util.List;

public class DbNameList {
    private final List<String> databases;
    private boolean dirty;

    public DbNameList() {
        this.databases = new ArrayList<>();
    }

    public void addDatabase(String database) {
        this.getDatabases().add(database);
        this.setDirty(true);
    }

    public void removeDatabase(String name) {
        this.getDatabases().remove(name);
        this.setDirty(true);
    }

    public String getDatabase(String name) {
        for (String db : this.getDatabases()) {
            if (db.equals(name)) {
                return db;
            }
        }
        return null;
    }

    public List<String> getDatabases() {
        return databases;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DbNameList (\n");

        for (int i = 0; i < this.getDatabases().size(); i++) {
            sb.append(this.getDatabases().get(i));
            if (i < this.getDatabases().size() - 1) {
                sb.append(", \n");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public String toJSONObject() {
        return JSONSerializer.serializeDBList(this);
    }

    public int length() {
        return this.getDatabases().size();
    }
}

package fr.tanchou.structure;

import java.util.*;

public abstract class ASchema {
    private final String name;
    private final List<Table> tables;

    public ASchema(String name) {
        this.name = name;
        this.tables = new ArrayList<>();
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public Table getTable(String name) {
        for (Table t : tables) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public List<Table> getTables() {
        return tables;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ASchema: ").append(this.getName()).append("\n");
        for (Table table : this.getTables()) {
            sb.append(table.toString());
        }
        return sb.toString();
    }

    public boolean isDirty() {
        for (Table table : this.getTables()) {
            if (table.isDirty()) {
                return true;
            }
        }
        return false;
    }
}

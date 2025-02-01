package fr.tanchou.structure;

import java.util.*;

public class Schema {
    private final String name;
    private final List<Table> tables;

    public Schema(String name) {
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
        sb.append(this.getName()).append(" (\n");

        for (int i = 0; i < this.getTables().size(); i++) {
            sb.append(this.getTables().get(i));
            if (i < this.getTables().size() - 1) {
                sb.append(", \n");
            }
        }
        sb.append(")");
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

package fr.tanchou.structure;

import java.util.*;

public class Table {
    private final String name;
    private final List<Column> columns;
    private final List<Key> index;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.index = new ArrayList<>();
    }

    public void addColumn(Column column) {
        this.getColumns().add(column);
    }

    public void addKey(Key index) {
        this.getIndex().add(index);
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Key> getIndex() {
        return index;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append(" (\nColumn : \n");
        for (int i = 0; i < this.getColumns().size(); i++) {
            sb.append(this.getColumns().get(i));
            if (i < this.getColumns().size() - 1) {
                sb.append(", \n");
            }
        }

        if (!this.getIndex().isEmpty()) {
            sb.append("\nConstraint : \n");

            for (int i = 0; i < this.getIndex().size(); i++) {
                sb.append(this.getIndex().get(i));
                if (i < this.getIndex().size() - 1) {
                    sb.append(", \n");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public boolean isDirty() {
        for (Column column : this.getColumns()) {
            if (column.isDirty()) {
                return true;
            }
        }
        return false;
    }

    public String getColumnNamePk() {
        for (Key key : this.getIndex()) {
            if (key instanceof PrimaryKey) {
                return key.getColumnName();
            }
        }
        return null;
    }
}

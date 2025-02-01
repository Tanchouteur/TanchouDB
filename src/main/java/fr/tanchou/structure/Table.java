package fr.tanchou.structure;

import java.util.*;

public class Table {
    private final String name;
    private final List<Column> columns;
    private final List<Constraint> constraints;

    public Table(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    public void addColumn(Column column) {
        this.getColumns().add(column);
    }

    public void addConstraint(Constraint constraint) {
        this.getConstraints().add(constraint);
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Constraint> getConstraints() {
        return constraints;
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

        if (!this.getConstraints().isEmpty()) {
            sb.append("\nConstraint : \n");

            for (int i = 0; i < this.getConstraints().size(); i++) {
                sb.append(this.getConstraints().get(i));
                if (i < this.getConstraints().size() - 1) {
                    sb.append(", \n");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }
}

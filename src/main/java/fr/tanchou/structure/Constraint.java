package fr.tanchou.structure;

import fr.tanchou.enums.ConstraintType;

public class Constraint {
    private final ConstraintType type;
    private final String columnName;

    public Constraint(ConstraintType type, String columnName) {
        this.type = type;
        this.columnName = columnName;
    }

    public ConstraintType getType() {
        return type;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toString() {
        return this.getType() + " (" + this.getColumnName() + ")";
    }
}

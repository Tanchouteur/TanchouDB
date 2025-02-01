package fr.tanchou.structure;

public class Column {
    private final String name;
    private final PrimitiveType type;
    private boolean nullable;
    private final boolean isPrimaryKey;
    private final boolean isForeignKey;

    private boolean dirty;

    public Column(String name, PrimitiveType type, boolean isPrimaryKey, boolean isForeignKey, boolean nullable) {
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public PrimitiveType getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
        this.dirty = true;
    }

    public boolean isForeignKey() {
        return isForeignKey;
    }

    @Override
    public String toString() {
        return name + " " + type + (isPrimaryKey ? " PRIMARY KEY" : "") + (nullable ? "" : " NOT NULL");
    }

    public boolean isDirty() {
        return dirty;
    }
}

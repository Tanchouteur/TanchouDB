package fr.tanchou.structure;

import fr.tanchou.enums.PrimitiveType;

public class Column {
    private final String name;
    private final PrimitiveType type;
    private boolean nullable;
    private final boolean isPrimaryKey;
    private final boolean isForeignKey;
    private final boolean isUnique;
    private final boolean autoIncrement;
    private String defaultValue;

    private boolean dirty;

    public Column(String name, PrimitiveType primitiveType,boolean isPrimaryKey, boolean isForeignKey, boolean nullable, boolean isUnique, boolean autoIncrement, String defaultValue) {
        this.name = name;
        this.type = primitiveType;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.nullable = nullable;
        this.isUnique = isUnique;
        this.autoIncrement = autoIncrement;
        this.defaultValue = defaultValue;

        this.dirty = true;
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

    public boolean isUnique() {
        return isUnique;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    @Override
    public String toString() {
        return name + " " + type + (isPrimaryKey ? " PRIMARY KEY" : "") + (nullable ? "" : " NOT NULL");
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean b) {
        this.dirty = b;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}

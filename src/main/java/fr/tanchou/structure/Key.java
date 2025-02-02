package fr.tanchou.structure;

import fr.tanchou.enums.KeyType;

public abstract class Key {
    private final String columnName;
    private final String tableName;
    private final KeyType keyType;

    public Key(String columnName, String tableName, KeyType keyType) {
        this.columnName = columnName;
        this.tableName = tableName;
        this.keyType = keyType;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    @Override
    public String toString() {
        return "Key{" +
                "columnName='" + columnName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", keyType=" + keyType +
                '}';
    }
}

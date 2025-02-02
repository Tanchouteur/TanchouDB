package fr.tanchou.structure;

import fr.tanchou.enums.KeyType;

public class PrimaryKey extends Key {
    public PrimaryKey(String columnName, String tableName) {
        super(columnName, tableName, KeyType.PRIMARY_KEY);
    }
}

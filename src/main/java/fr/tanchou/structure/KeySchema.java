package fr.tanchou.structure;

import fr.tanchou.enums.PrimitiveType;

public class KeySchema extends ASchema {
    public KeySchema() {
        super("keySchema");

        this.addTable(createPKTable());
        this.addTable(createFKTable());
        this.addTable(createIndexTable());
    }

    private Table createPKTable() {
        Table pk = new Table("Primary_Key_Table");

        pk.addColumn(getPkColumn());
        pk.addColumn(new Column("targetTable", PrimitiveType.VARCHAR, false, false, false, true, false, null));
        pk.addColumn(new Column("targetColumn", PrimitiveType.VARCHAR, false, false, false, true, false, null));

        return pk;
    }

    private Table createFKTable() {
        Table fk = new Table("Foreign_Key_Table");

        fk.addColumn(getPkColumn());
        fk.addColumn(new Column("targetTable", PrimitiveType.VARCHAR, false, false, false, false, false, null));
        fk.addColumn(new Column("targetColumn", PrimitiveType.VARCHAR, false, false, false, false, false, null));
        fk.addColumn(new Column("sourceTable", PrimitiveType.VARCHAR, false, false, false, false, false, null));
        fk.addColumn(new Column("sourceColumn", PrimitiveType.VARCHAR, false, false, false, false, false, null));

        return fk;
    }

    private Table createIndexTable() {
        Table index = new Table("Index_Table");

        index.addColumn(getPkColumn());
        index.addColumn(new Column("targetTable", PrimitiveType.VARCHAR, false, false, false, false, false, null));
        index.addColumn(new Column("targetColumn", PrimitiveType.VARCHAR, false, false, false, false, false, null));

        return index;
    }

    private Column getPkColumn() {
        return new Column("id", PrimitiveType.INTEGER, true, false, false, true, true, null);
    }
}

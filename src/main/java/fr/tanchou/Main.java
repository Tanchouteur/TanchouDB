package fr.tanchou;

import fr.tanchou.structure.*;

public class Main {
    public static void main(String[] args) {

        DbManager dbManager = new DbManager();

        dbManager.listDatabases();

        Database db = new Database("db1");
        Schema schema = new Schema("schema1");
        Table table = new Table("table1");
        table.addColumn(new Column("col1", PrimitiveType.INTEGER, true, false, false));
        table.addColumn(new Column("col2", PrimitiveType.VARCHAR, false, false, true));
        schema.addTable(table);
        db.addSchema(schema);

        dbManager.addDatabase(db);

        dbManager.listDatabases();

        dbManager.commit();
    }
}

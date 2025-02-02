package fr.tanchou.tester;

import fr.tanchou.enums.ConstraintType;
import fr.tanchou.enums.PrimitiveType;
import fr.tanchou.structure.utils.IStorageManager;
import fr.tanchou.structure.utils.JSONParser;
import fr.tanchou.structure.utils.LocalStorageManager;
import fr.tanchou.structure.*;

public class ReadDBMain {
    public static void main(String[] args) {
        IStorageManager storageManager = new LocalStorageManager();
        Database db = JSONParser.parseDatabase(storageManager.readFromFile("MyDB"));

        Schema schema = new Schema("Public");
        db.addSchema(schema);

        Table users = new Table("Users");
        users.addColumn(new Column("id", PrimitiveType.INTEGER, true, false, false));
        users.addColumn(new Column("name", PrimitiveType.VARCHAR, false, false, false));
        users.addConstraint(new Constraint(ConstraintType.PRIMARY_KEY, "id"));

        schema.addTable(users);
        storageManager.writeToFile(db.getName(), db.toJSONObject());

    }
}

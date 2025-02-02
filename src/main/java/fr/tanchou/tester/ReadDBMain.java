package fr.tanchou.tester;

import fr.tanchou.enums.ConstraintType;
import fr.tanchou.enums.PrimitiveType;
import fr.tanchou.structure.utils.JSONParser;
import fr.tanchou.structure.utils.StorageManager;
import fr.tanchou.structure.*;

public class ReadDBMain {
    public static void main(String[] args) {
        Database db = JSONParser.parseDatabase(StorageManager.readFromFile("MyDB"));

        Schema schema = new Schema("Public");
        db.addSchema(schema);

        Table users = new Table("Users");
        users.addColumn(new Column("id", PrimitiveType.INTEGER, true, false, false));
        users.addColumn(new Column("name", PrimitiveType.VARCHAR, false, false, false));
        users.addConstraint(new Constraint(ConstraintType.PRIMARY_KEY, "id"));

        schema.addTable(users);

        StorageManager.writeToFile(db.getName(), db.toJSONObject());

    }
}

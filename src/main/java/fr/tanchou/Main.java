package fr.tanchou;

import fr.tanchou.structure.*;

public class Main {
    public static void main(String[] args) {
        Database db = new Database("MyDB");

        Schema schema = new Schema("Public");
        db.addSchema(schema);

        Table users = new Table("Users");
        users.addColumn(new Column("id", PrimitiveType.INTEGER, true, false, false));
        users.addColumn(new Column("name", PrimitiveType.VARCHAR, false, false, false));
        users.addConstraint(new Constraint(ConstraintType.PRIMARY_KEY, "id"));

        schema.addTable(users);

    }
}

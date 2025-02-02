package fr.tanchou.structure;

import fr.tanchou.enums.PrimitiveType;

public class ConstraintSchema extends ASchema {
    public ConstraintSchema() {
        super("constraintSchema");
        Table referentialIntegrityTable = new Table("referentialIntegrityTable");

        Column idRefConstraint = new Column("idRefConstraint", PrimitiveType.INTEGER,true, false, false, true, true, null);
        Column nameRefConstraint = new Column("nameRefConstraint", PrimitiveType.VARCHAR,false, false, false, true, false, "Constraint_default");
        Column key1RefConstraint = new Column("key1RefConstraint", PrimitiveType.KEY,false, true, false, true, false, null);
        Column key2RefConstraint = new Column("key2RefConstraint", PrimitiveType.KEY,false, true, false, true, false, null);

        referentialIntegrityTable.addColumn(idRefConstraint);
        referentialIntegrityTable.addColumn(nameRefConstraint);
        referentialIntegrityTable.addColumn(key1RefConstraint);
        referentialIntegrityTable.addColumn(key2RefConstraint);

        this.addTable(referentialIntegrityTable);
    }
}

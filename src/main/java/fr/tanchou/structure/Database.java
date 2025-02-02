package fr.tanchou.structure;

import fr.tanchou.structure.utils.JSONSerializer;
import fr.tanchou.structure.utils.Serializer;

import java.util.*;

public class Database {
    private final String name;
    private final Map<String, ASchema> Schemas;

    private final Serializer<String> serializer = JSONSerializer.getInstance();

    public Database(String name, Map<String, ASchema> schemas) {

        if (schemas.size() < 3){
            throw new IllegalArgumentException("Database must have at least 3 schemas");
        }else if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Database name cannot be null or empty");
        }else if (name.equals("TanchouDBUser") || name.equals("TanchouDBConstraint")){
            throw new IllegalArgumentException("Database name cannot be TanchouDBUser or TanchouDBConstraint");
        }

        this.name = name;
        this.Schemas = schemas;
    }

    public void addTable(Table table) {
        this.getUserSchema().addTable(table);
    }

    public ASchema getUserSchema() {
        return getSchemas().get("user");
    }

    private ASchema getConstraintSchema() {
        return getSchemas().get("constraint");
    }

    public String getName() {
        return name;
    }

    public Map<String, ASchema> getSchemas() {
        return Schemas;
    }

    @Override
    public String toString() {
        return this.toJSONObject();
    }

    public String toJSONObject() {
        return this.getSerializer().serializeDatabase(this);
    }

    public boolean isDirty() {
        return getUserSchema().isDirty() || getConstraintSchema().isDirty();
    }

    private Serializer<String> getSerializer() {
        return serializer;
    }
}

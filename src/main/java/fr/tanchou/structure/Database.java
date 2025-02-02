package fr.tanchou.structure;

import fr.tanchou.utils.JSONSerializer;
import fr.tanchou.utils.Serializer;

import java.util.*;

public class Database {
    private final String name;
    private final Map<String, ASchema> Schemas;

    private boolean dirty = false;

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
        this.dirty = true;
    }

    public ASchema getUserSchema() {
        return getSchemas().get("userSchema");
    }

    private ASchema getConstraintSchema() {
        return getSchemas().get("constraintSchema");
    }

    private ASchema getKeySchema() {
        return getSchemas().get("keySchema");
    }

    public String getName() {
        return name;
    }

    public Map<String, ASchema> getSchemas() {
        return Schemas;
    }

    public boolean isDirty() {
        return getUserSchema().isDirty() || getConstraintSchema().isDirty() || getKeySchema().isDirty() || dirty;
    }

    private Serializer<String> getSerializer() {
        return serializer;
    }

    public String toJSONObject() {
        return this.getSerializer().serializeDatabase(this);
    }

    @Override
    public String toString() {
        return this.toJSONObject();
    }
}

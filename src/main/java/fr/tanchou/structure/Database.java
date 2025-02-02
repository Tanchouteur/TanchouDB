package fr.tanchou.structure;

import fr.tanchou.structure.utils.JSONSerializer;
import fr.tanchou.structure.utils.Serializer;

import java.util.*;

public class Database {
    private final String name;
    private final Map<String, Schema> Schemas;

    private final Serializer<String> serializer = JSONSerializer.getInstance();

    public Database(String name, Map<String, Schema> schemas) {
        this.name = name;
        this.Schemas = schemas;
    }

    public void addTable(Table table) {
        this.getUserSchema().addTable(table);
    }

    public Schema getUserSchema() {
        return getSchemas().get("user");
    }

    private Schema getConstraintSchema() {
        return getSchemas().get("constraint");
    }

    public String getName() {
        return name;
    }

    public Map<String, Schema> getSchemas() {
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

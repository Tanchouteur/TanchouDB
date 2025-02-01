package fr.tanchou.structure;

import fr.tanchou.storage.JSONSerializer;

import java.util.*;

public class Database {
    private final String name;
    private final List<Schema> schemas;

    public Database(String name) {
        this.name = name;
        this.schemas = new ArrayList<>();
    }

    public void addSchema(Schema schema) {
        this.getSchemas().add(schema);
    }

    public Schema getSchema(String name) {
        for (Schema s : schemas) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append(" (\n");

        for (int i = 0; i < this.getSchemas().size(); i++) {
            sb.append(this.getSchemas().get(i));
            if (i < this.getSchemas().size() - 1) {
                sb.append(", \n");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public String toJSONObject() {
        return JSONSerializer.serializeDatabase(this);
    }
}

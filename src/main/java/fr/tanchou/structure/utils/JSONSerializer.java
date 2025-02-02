package fr.tanchou.structure.utils;

import fr.tanchou.structure.*;

public class JSONSerializer implements Serializer<String> {
    private static JSONSerializer instance;

    public static JSONSerializer getInstance() {
        if (instance == null) {
            instance = new JSONSerializer();
        }
        return instance;
    }

    private JSONSerializer() {
    }

    public String serializeDatabase(Database db) {
        if (db == null) throw new IllegalArgumentException("Database cannot be null");

        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("\"name\": \"").append(db.getName()).append("\",\n");
        json.append("\"schemas\": [\n");

        for (ASchema ASchema : db.getSchemas().values()) {
            json.append("  { \"name\": \"").append(ASchema.getName()).append("\",\n");
            json.append("    \"tables\": [\n");

            for (int j = 0; j < ASchema.getTables().size(); j++) {
                Table table = ASchema.getTables().get(j);
                json.append("      { \"name\": \"").append(table.getName()).append("\",\n");
                json.append("        \"columns\": [\n");

                for (int k = 0; k < table.getColumns().size(); k++) {
                    Column col = table.getColumns().get(k);
                    json.append("          { \"name\": \"").append(col.getName()).append("\", ");
                    json.append("\"type\": \"").append(col.getType()).append("\", ");
                    json.append("\"primaryKey\": \"").append(col.isPrimaryKey()).append("\", ");
                    json.append("\"foreignKey\": \"").append(col.isForeignKey()).append("\", ");
                    json.append("\"nullable\": \"").append(col.isNullable()).append("\", ");
                    json.append("\"unique\": \"").append(col.isUnique()).append("\", ");
                    json.append("\"autoIncrement\": \"").append(col.isAutoIncrement()).append("\", ");
                    json.append("\"defaultValue\": \"").append(col.getDefaultValue()).append("\" }");

                    if (k < table.getColumns().size() - 1) json.append(",");
                    json.append("\n");
                }

                json.append("        ]\n");
                json.append("      }");

                if (j < ASchema.getTables().size() - 1) json.append(",");
                json.append("\n");
            }

            json.append("    ]\n");
            json.append("  }");

            if (!ASchema.getName().equals("constraint")) json.append(",");
            json.append("\n");
        }

        json.append("]\n}");
        return json.toString();
    }

    public String serializeDBList(DbNameList dbNameList) {
        if (dbNameList == null) throw new IllegalArgumentException("DbNameList cannot be null");

        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("\"databases\": [\n");

        for (int i = 0; i < dbNameList.getDatabases().size(); i++) {
            String db = dbNameList.getDatabases().get(i);
            json.append(db).append(",\n");
        }

        json.append("]\n}");
        return json.toString();
    }
}
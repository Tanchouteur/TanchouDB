package fr.tanchou.structure.utils;

import fr.tanchou.structure.*;

public class JSONSerializer {
    public static String serializeDatabase(Database db) {
        if (db == null) throw new IllegalArgumentException("Database cannot be null");

        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("\"name\": \"").append(db.getName()).append("\",\n");
        json.append("\"schemas\": [\n");

        for (int i = 0; i < db.getSchemas().size(); i++) {
            Schema schema = db.getSchemas().get(i);
            json.append("  { \"name\": \"").append(schema.getName()).append("\",\n");
            json.append("    \"tables\": [\n");

            for (int j = 0; j < schema.getTables().size(); j++) {
                Table table = schema.getTables().get(j);
                json.append("      { \"name\": \"").append(table.getName()).append("\",\n");
                json.append("        \"columns\": [\n");

                for (int k = 0; k < table.getColumns().size(); k++) {
                    Column col = table.getColumns().get(k);
                    json.append("          { \"name\": \"").append(col.getName()).append("\", ");
                    json.append("\"type\": \"").append(col.getType()).append("\", ");
                    json.append("\"primaryKey\": \"").append(col.isPrimaryKey()).append("\", ");
                    json.append("\"foreignKey\": \"").append(col.isForeignKey()).append("\", ");
                    json.append("\"nullable\": \"").append(col.isNullable()).append("\" }");

                    if (k < table.getColumns().size() - 1) json.append(",");
                    json.append("\n");
                }

                json.append("        ]\n");
                json.append("      }");

                if (j < schema.getTables().size() - 1) json.append(",");
                json.append("\n");
            }

            json.append("    ]\n");
            json.append("  }");

            if (i < db.getSchemas().size() - 1) json.append(",");
            json.append("\n");
        }

        json.append("]\n}");
        return json.toString();
    }

    public static String serializeDBList(DbNameList dbNameList) {
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
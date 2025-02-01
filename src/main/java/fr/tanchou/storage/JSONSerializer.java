package fr.tanchou.storage;

import fr.tanchou.structure.Column;
import fr.tanchou.structure.Database;
import fr.tanchou.structure.Schema;
import fr.tanchou.structure.Table;

public class JSONSerializer {
    public static String serializeDatabase(Database db) {
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
}
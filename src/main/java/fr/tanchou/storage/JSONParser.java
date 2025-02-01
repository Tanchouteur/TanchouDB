package fr.tanchou.storage;

import fr.tanchou.structure.*;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public static Database parseDatabase(String jsonString) {
        jsonString = jsonString.trim(); // Remove leading/trailing whitespace
        if (!jsonString.startsWith("{") || !jsonString.endsWith("}")) {
            throw new IllegalArgumentException("Invalid JSON: Must start with '{' and end with '}'");
        }

        // Remove the outer braces
        jsonString = jsonString.substring(1, jsonString.length() - 1).trim();

        Database database = new Database(extractValue(jsonString, "name"));

        // Extract the "schemas" array
        String schemasString = extractArray(jsonString, "schemas");
        List<Schema> schemas = parseSchemas(schemasString);
        for (Schema schema : schemas) {
            database.addSchema(schema);
        }

        return database;
    }

    private static List<Schema> parseSchemas(String schemasString) {
        List<Schema> schemas = new ArrayList<>();
        String[] schemaTokens = splitJsonArray(schemasString);

        for (String schemaToken : schemaTokens) {
            String schemaName = extractValue(schemaToken, "name");
            Schema schema = new Schema(schemaName);

            // Extract the "tables" array
            String tablesString = extractArray(schemaToken, "tables");
            List<Table> tables = parseTables(tablesString);
            for (Table table : tables) {
                schema.addTable(table);
            }

            schemas.add(schema);
        }

        return schemas;
    }

    private static List<Table> parseTables(String tablesString) {
        List<Table> tables = new ArrayList<>();
        String[] tableTokens = splitJsonArray(tablesString);

        for (String tableToken : tableTokens) {
            String tableName = extractValue(tableToken, "name");
            Table table = new Table(tableName);

            // Extract the "columns" array
            String columnsString = extractArray(tableToken, "columns");
            List<Column> columns = parseColumns(columnsString);
            for (Column column : columns) {
                table.addColumn(column);
            }

            tables.add(table);
        }

        return tables;
    }

    private static List<Column> parseColumns(String columnsString) {
        List<Column> columns = new ArrayList<>();
        String[] columnTokens = splitJsonArray(columnsString);

        for (String columnToken : columnTokens) {
            String name = extractValue(columnToken, "name");
            String type = extractValue(columnToken, "type");
            boolean primaryKey = Boolean.parseBoolean(extractValue(columnToken, "primaryKey"));
            boolean foreignKey = Boolean.parseBoolean(extractValue(columnToken, "foreignKey"));
            boolean nullable = Boolean.parseBoolean(extractValue(columnToken, "nullable"));

            Column column = new Column(name, PrimitiveType.valueOf(type), primaryKey, foreignKey, nullable);
            columns.add(column);
        }

        return columns;
    }

    private static String extractValue(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":";
        int keyIndex = json.indexOf(keyWithQuotes);
        if (keyIndex == -1) {
            throw new IllegalArgumentException("Key '" + key + "' not found in JSON: " + json);
        }

        int valueStart = json.indexOf('"', keyIndex + keyWithQuotes.length()) + 1;
        int valueEnd = json.indexOf('"', valueStart);
        return json.substring(valueStart, valueEnd);
    }

    private static String extractArray(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":";
        int keyIndex = json.indexOf(keyWithQuotes);
        if (keyIndex == -1) {
            throw new IllegalArgumentException("Key '" + key + "' not found in JSON: " + json);
        }

        int arrayStart = json.indexOf('[', keyIndex + keyWithQuotes.length());
        int arrayEnd = findMatchingBracket(json, arrayStart);
        return json.substring(arrayStart + 1, arrayEnd).trim();
    }

    private static String[] splitJsonArray(String jsonArray) {
        List<String> tokens = new ArrayList<>();
        int depth = 0;
        int start = 0;

        for (int i = 0; i < jsonArray.length(); i++) {
            char c = jsonArray.charAt(i);
            if (c == '{') depth++;
            else if (c == '}') depth--;
            else if (c == ',' && depth == 0) {
                tokens.add(jsonArray.substring(start, i).trim());
                start = i + 1;
            }
        }

        if (start < jsonArray.length()) {
            tokens.add(jsonArray.substring(start).trim());
        }

        return tokens.toArray(new String[0]);
    }

    private static int findMatchingBracket(String json, int start) {
        int depth = 1;
        for (int i = start + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') depth++;
            else if (c == ']') depth--;
            if (depth == 0) return i;
        }
        throw new IllegalArgumentException("Unmatched brackets in JSON: " + json);
    }
}
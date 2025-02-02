package fr.tanchou.structure.utils;

import fr.tanchou.structure.Schema;
import fr.tanchou.enums.PrimitiveType;
import fr.tanchou.structure.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser implements Parser<String> {

    private static JSONParser instance;

    public static JSONParser getInstance() {
        if (instance == null) {
            instance = new JSONParser();
        }
        return instance;
    }

    private JSONParser() {
    }

    public Database parseDatabase(String jsonString) {
        jsonString = jsonString.trim(); // Remove leading/trailing whitespace
        if (!jsonString.startsWith("{") || !jsonString.endsWith("}")) {
            throw new IllegalArgumentException("Invalid JSON: Must start with '{' and end with '}'" + jsonString);
        }

        // Remove the outer braces
        jsonString = jsonString.substring(1, jsonString.length() - 1).trim();


        // Extract the "schemas" array
        String schemasString = extractArray(jsonString, "schemas");
        Map<String, ASchema> schemas = parseSchemas(schemasString);

        return new Database(extractValue(jsonString, "name"), schemas);
    }

    private Map<String, ASchema> parseSchemas(String schemasString) {
        Map<String, ASchema> schemas = new HashMap<>(3);
        String[] schemaTokens = splitJsonArray(schemasString);

        for (String schemaToken : schemaTokens) {
            String schemaName = extractValue(schemaToken, "name");

            // Extract the "tables" array
            String tablesString = extractArray(schemaToken, "tables");
            List<Table> tables = parseTables(tablesString);

            ASchema schema = new Schema(schemaName);

            for (Table table : tables) {
                schema.addTable(table);
            }

            schemas.put(schemaName, schema);

        }

        return schemas;
    }

    private List<Table> parseTables(String tablesString) {
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

    private List<Column> parseColumns(String columnsString) {
        List<Column> columns = new ArrayList<>();
        String[] columnTokens = splitJsonArray(columnsString);

        for (String columnToken : columnTokens) {
            String name = extractValue(columnToken, "name");
            String type = extractValue(columnToken, "type");
            boolean primaryKey = Boolean.parseBoolean(extractValue(columnToken, "primaryKey"));
            boolean foreignKey = Boolean.parseBoolean(extractValue(columnToken, "foreignKey"));
            boolean nullable = Boolean.parseBoolean(extractValue(columnToken, "nullable"));
            boolean unique = Boolean.parseBoolean(extractValue(columnToken, "unique"));
            boolean autoIncrement = Boolean.parseBoolean(extractValue(columnToken, "autoIncrement"));
            String defaultValue = extractValue(columnToken, "defaultValue");

            Column column = new Column(name, PrimitiveType.valueOf(type), primaryKey, foreignKey, nullable, unique, autoIncrement, defaultValue);
            column.setDirty(false);
            columns.add(column);
        }

        return columns;
    }

    private String extractValue(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":";
        int keyIndex = json.indexOf(keyWithQuotes);
        if (keyIndex == -1) {
            throw new IllegalArgumentException("Key '" + key + "' not found in JSON: " + json);
        }

        int valueStart = json.indexOf('"', keyIndex + keyWithQuotes.length()) + 1;
        int valueEnd = json.indexOf('"', valueStart);
        return json.substring(valueStart, valueEnd);
    }

    private String extractArray(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":";
        int keyIndex = json.indexOf(keyWithQuotes);
        if (keyIndex == -1) {
            throw new IllegalArgumentException("Key '" + key + "' not found in JSON: " + json);
        }

        int arrayStart = json.indexOf('[', keyIndex + keyWithQuotes.length());
        int arrayEnd = findMatchingBracket(json, arrayStart);
        return json.substring(arrayStart + 1, arrayEnd).trim();
    }

    private String[] splitJsonArray(String jsonArray) {
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

    private int findMatchingBracket(String json, int start) {
        int depth = 1;
        for (int i = start + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') depth++;
            else if (c == ']') depth--;
            if (depth == 0) return i;
        }
        throw new IllegalArgumentException("Unmatched brackets in JSON: " + json);
    }

    public DbNameList parseDBList(String jsonString) {

        if (jsonString == null || jsonString.isEmpty()) {
            return new DbNameList();
        }else {

            jsonString = jsonString.trim(); // Remove leading/trailing whitespace

            if (!jsonString.startsWith("{") || !jsonString.endsWith("}")) {
                throw new IllegalArgumentException("Invalid JSON: Must start with '{' and end with '}'");
            }

            // Remove the outer braces
            jsonString = jsonString.substring(1, jsonString.length() - 1).trim();

            DbNameList dbNameList = new DbNameList();

            // Extract the "databases" array
            String databasesString = extractArray(jsonString, "databases");
            String[] databasesName = splitJsonArray(databasesString);
            for (String database : databasesName) {
                dbNameList.addDatabase(database);
            }

            dbNameList.setDirty(false);

            return dbNameList;
        }
    }
}
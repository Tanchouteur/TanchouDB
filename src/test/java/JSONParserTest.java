import fr.tanchou.structure.utils.JSONParser;
import fr.tanchou.structure.*;
import fr.tanchou.structure.utils.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JSONParserTest {

    @Test
    void testParseDatabase() {
        Parser<String> parser = JSONParser.getInstance();

        String json = "{\n" +
                "\"name\": \"TestDB\",\n" +
                "\"schemas\": [\n" +
                "  { \"name\": \"Public\",\n" +
                "    \"tables\": [\n" +
                "      { \"name\": \"Users\",\n" +
                "        \"columns\": [\n" +
                "          { \"name\": \"id\", \"type\": \"INTEGER\", \"primaryKey\": \"true\", \"foreignKey\": \"false\", \"nullable\": \"false\" },\n" +
                "          { \"name\": \"name\", \"type\": \"VARCHAR\", \"primaryKey\": \"false\", \"foreignKey\": \"false\", \"nullable\": \"true\" },\n" +
                "          { \"name\": \"email\", \"type\": \"VARCHAR\", \"primaryKey\": \"false\", \"foreignKey\": \"true\", \"nullable\": \"false\" }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]\n" +
                "}";

        // Parse the JSON string
        Database db = parser.parseDatabase(json);

        // Test the database name
        assertEquals("TestDB", db.getName());

        // Test the schema name
        Schema publicSchema = db.getSchemas().get(0);
        assertEquals("Public", publicSchema.getName());

        // Test the table name
        Table usersTable = publicSchema.getTables().get(0);
        assertEquals("Users", usersTable.getName());

        // Test columns
        Column idColumn = usersTable.getColumns().get(0);
        assertEquals("id", idColumn.getName());
        assertTrue(idColumn.isPrimaryKey());
        assertFalse(idColumn.isForeignKey());
        assertFalse(idColumn.isNullable());

        Column nameColumn = usersTable.getColumns().get(1);
        assertEquals("name", nameColumn.getName());
        assertFalse(nameColumn.isPrimaryKey());
        assertFalse(nameColumn.isForeignKey());
        assertTrue(nameColumn.isNullable());

        Column emailColumn = usersTable.getColumns().get(2);
        assertEquals("email", emailColumn.getName());
        assertFalse(emailColumn.isPrimaryKey());
        assertTrue(emailColumn.isForeignKey());
        assertFalse(emailColumn.isNullable());
    }

    @Test
    void testParseDatabaseWithMultipleSchemas() {
        Parser<String> parser = JSONParser.getInstance();

        String json = "{\n" +
                "\"name\": \"TestDB\",\n" +
                "\"schemas\": [\n" +
                "  { \"name\": \"Public\",\n" +
                "    \"tables\": [\n" +
                "      { \"name\": \"Users\",\n" +
                "        \"columns\": [\n" +
                "          { \"name\": \"id\", \"type\": \"INTEGER\", \"primaryKey\": \"true\", \"foreignKey\": \"false\", \"nullable\": \"false\" },\n" +
                "          { \"name\": \"name\", \"type\": \"VARCHAR\", \"primaryKey\": \"false\", \"foreignKey\": \"false\", \"nullable\": \"true\" },\n" +
                "          { \"name\": \"email\", \"type\": \"VARCHAR\", \"primaryKey\": \"false\", \"foreignKey\": \"true\", \"nullable\": \"false\" }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  { \"name\": \"Private\",\n" +
                "    \"tables\": [\n" +
                "      { \"name\": \"Admins\",\n" +
                "        \"columns\": [\n" +
                "          { \"name\": \"id\", \"type\": \"INTEGER\", \"primaryKey\": \"true\", \"foreignKey\": \"false\", \"nullable\": \"false\" },\n" +
                "          { \"name\": \"name\", \"type\": \"VARCHAR\", \"primaryKey\": \"false\", \"foreignKey\": \"false\", \"nullable\": \"true\" },\n" +
                "          { \"name\": \"email\", \"type\": \"VARCHAR\", \"primaryKey\": \"false\", \"foreignKey\": \"true\", \"nullable\": \"false\" }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]\n" +
                "}";

        // Parse the JSON string
        Database db = parser.parseDatabase(json);

        // Test the database name
        assertEquals("TestDB", db.getName());

        // Test the schema list
        assertEquals(2, db.getSchemas().size());

        // Test the first schema
        Schema publicSchema = db.getSchemas().get(0);
        assertEquals("Public", publicSchema.getName());

        // Test the first table
        Table usersTable = publicSchema.getTables().get(0);
        assertEquals("Users", usersTable.getName());

        // Test the second schema
        Schema privateSchema = db.getSchemas().get(1);
        assertEquals("Private", privateSchema.getName());

        // Test the second table
        Table adminsTable = privateSchema.getTables().get(0);
        assertEquals("Admins", adminsTable.getName());

        // Test columns
        Column idColumn = usersTable.getColumns().get(0);
        assertEquals("id", idColumn.getName());
        assertTrue(idColumn.isPrimaryKey());
        assertFalse(idColumn.isForeignKey());
        assertFalse(idColumn.isNullable());

        Column nameColumn = usersTable.getColumns().get(1);
        assertEquals("name", nameColumn.getName());
        assertFalse(nameColumn.isPrimaryKey());
        assertFalse(nameColumn.isForeignKey());
        assertTrue(nameColumn.isNullable());
    }

    @Test
    void testMissingKey() {
        Parser<String> parser = JSONParser.getInstance();

        String json = "{\n" +
                "\"name\": ,\n" +
                "\"schemas\": [\n" +
                "  { \"name\": ,\n" +
                "    \"tables\": [\n" +
                "      { \"name\": \"Users\",\n" +
                "        \"columns\": [\n" +
                "          { \"name\": \"id\", \"type\": \"INTEGER\", \"primaryKey\": \"true\", \"foreignKey\": \"false\", \"nullable\": \"false\" },\n" +
                "          { \"name\": \"name\", \"type\": \"VARCHAR\", \"primaryKey\": \"false\", \"foreignKey\": \"false\", \"nullable\": \"true\" },\n" +
                "          { \"name\": \"email\", \"type\": \"VARCHAR\", \"primaryKey\": \"false\", \"foreignKey\": \"true\", \"nullable\": \"false\" }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]\n" +
                "}";
        String invalidJson = json.replace("\"name\": \"TestDB\",\n", "");

        assertThrows(IllegalArgumentException.class, () -> parser.parseDatabase(invalidJson));
    }

    @Test
    void testParseEmptyDatabase() {
        Parser<String> parser = JSONParser.getInstance();

        String json = "{\n" +
                "\"name\": \"EmptyDB\",\n" +
                "\"schemas\": []\n" +
                "}";

        // Parse the JSON string
        Database db = parser.parseDatabase(json);

        // Test the database name
        assertEquals("EmptyDB", db.getName());

        // Test the schema list
        assertTrue(db.getSchemas().isEmpty());
    }

    @Test
    void testEmptyParameter() {
        Parser<String> parser = JSONParser.getInstance();

        assertThrows(IllegalArgumentException.class, () -> parser.parseDatabase(""));
    }

    @Test
    void testInvalidJson() {
        Parser<String> parser = JSONParser.getInstance();

        assertThrows(IllegalArgumentException.class, () -> parser.parseDatabase("{"));
    }
}

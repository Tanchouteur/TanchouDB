import fr.tanchou.storage.JSONParser;
import fr.tanchou.structure.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JSONParserTest {

    @Test
    void testParseDatabase() {
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
        Database db = JSONParser.parseDatabase(json);

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
}

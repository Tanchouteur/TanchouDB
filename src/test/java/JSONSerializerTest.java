import fr.tanchou.storage.JSONSerializer;
import fr.tanchou.structure.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JSONSerializerTest {

    @Test
    void testSerializeDatabase() {
        // Create a database
        Database db = new Database("TestDB");

        // Create a schema
        Schema schema = new Schema("Public");
        db.addSchema(schema);

        // Create a table
        Table users = new Table("Users");
        users.addColumn(new Column("id", PrimitiveType.INTEGER, true, false, false)); // Primary Key
        users.addColumn(new Column("name", PrimitiveType.VARCHAR, false, false, true)); // Nullable
        users.addColumn(new Column("email", PrimitiveType.VARCHAR, false, true, false)); // Foreign Key

        schema.addTable(users);

        // Expected JSON output
        String expected = "{\n" +
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

        // Assert the serialization output
        assertEquals(expected, JSONSerializer.serializeDatabase(db));
    }
}
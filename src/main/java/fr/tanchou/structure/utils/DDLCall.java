package fr.tanchou.structure.utils;

import fr.tanchou.dataInstance.Tuple;
import fr.tanchou.enums.KeyType;
import fr.tanchou.structure.*;

import java.util.HashMap;
import java.util.Map;

public class DDLCall {

    public static Database createDatabase(String dbName){
        ASchema userASchema = new UserSchema();
        ASchema constraintASchema = new ConstraintSchema();

        Map<String, ASchema> schemas = new HashMap<>(3);
        schemas.put("user", userASchema);
        schemas.put("constraint", constraintASchema);
        schemas.put("key", new KeySchema());

        return new Database(dbName, schemas);
    }

    public static Tuple createIndex(String dbName, String name, String name1, KeyType keyType) {
        FullBufferStructure bufferStructure = FullBufferStructure.getInstance();

        return null;
    }
}

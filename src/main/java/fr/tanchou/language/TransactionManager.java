package fr.tanchou.language;

import fr.tanchou.dataInstance.BufferData;
import fr.tanchou.dataInstance.FullBufferData;
import fr.tanchou.enums.InstructionType;
import fr.tanchou.enums.StructureType;
import fr.tanchou.structure.Increment;
import fr.tanchou.structure.IncrementClassic;
import fr.tanchou.structure.Table;
import fr.tanchou.structure.utils.BufferStructure;
import fr.tanchou.structure.utils.DbManager;
import fr.tanchou.structure.utils.FullBufferStructure;
import fr.tanchou.structure.utils.IDbManager;
import fr.tanchou.utils.TransactionResult;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private final Increment transactionId;

    public TransactionManager() {
        this.transactionId = new IncrementClassic();
    }

    // Parser like
    public Transaction createTransaction(String sql) {
        List<List<String>> sqlLines = new ArrayList<>();

        String[] sqlLine = sql.split(";");//Operation

        List<Operation> operations = new ArrayList<>(sqlLine.length+1);

        for (String part : sqlLine){
            String[] words = part.split(" ");

            InstructionType instructionType = InstructionType.valueOf(words[0].toUpperCase());
            Operation operation = null;
            switch (instructionType) {
                case CREATE:
                case UPDATE:
                case DELETE:
                    if (words.length < 3) {
                        throw new IllegalArgumentException("Invalid SQL statement");
                    }else {
                        List<String> parameters = new ArrayList<>();
                        for (int i = 2; i < words.length; i++) {
                            parameters.add(words[i]);
                        }
                        sqlLines.add(parameters);
                        operation = new Operation(instructionType, StructureType.valueOf(words[1]), parameters);
                    }
                    break;
                case SELECT:
                    if (words.length < 4) {
                        throw new IllegalArgumentException("Invalid SQL statement");
                    }
                    List<String> parameters = new ArrayList<>();
                    for (int i = 1; i < words.length; i++) {
                        parameters.add(words[i]);
                    }
                    operation = new Operation(instructionType, StructureType.COLUMN, parameters);
                    break;

                case COMMIT:
                    if (words.length > 1) {
                        throw new IllegalArgumentException("Invalid SQL statement");
                    }
                     operation = new Operation(instructionType, null, null);
                    break;
            }

            operations.add(operation);
        }

        return new Transaction(transactionId.increment(), operations);
    }

    public TransactionResult executeTransaction(Transaction transaction) {
        IDbManager dbManager = DbManager.getInstance();

        TransactionResult transactionResult = null;

        for (Operation operation : transaction.getOperations()) {
            if (operation.getStructureType() == null && operation.getInstructionType() == InstructionType.COMMIT) {
                FullBufferStructure.getInstance().commit();
                continue;
            }
            switch (operation.getStructureType()) {
                case DATABASE:
                    switch (operation.getInstructionType()) {
                        case CREATE:
                            dbManager.createDatabase(operation.getParameters().get(0));
                            transactionResult = new TransactionResult(true, "Database created");
                            break;
                        case UPDATE:
                            // Not implemented
                            break;
                        case DELETE:
                            dbManager.removeDatabase(operation.getParameters().get(0));
                            transactionResult = new TransactionResult(true, "Database deleted");
                            break;
                    }
                    break;

                case TABLE:
                    switch (operation.getInstructionType()) {
                        case CREATE:
                            dbManager.getDatabasesMap().get(operation.getParameters().get(0)).addTable(new Table(operation.getParameters().get(1)));
                            transactionResult = new TransactionResult(true, "Table created");
                            break;
                        case UPDATE:
                            // Not implemented
                            break;
                        case DELETE:
                            // Not implemented
                            break;
                    }
                    break;

                case COLUMN:
                    switch (operation.getInstructionType()) {
                        case CREATE:
                            // Not implemented
                            break;
                        case UPDATE:
                            // Not implemented
                            break;
                        case DELETE:
                            // Not implemented
                            break;

                        case SELECT:
                            transactionResult = new TransactionResult(true, FullBufferData.getInstance().getData(operation.getParameters().get(1), operation.getParameters().get(2)).toString());
                            break;

                    }
                    break;
            }
        }
        return transactionResult;
    }
}

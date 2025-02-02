package fr.tanchou.language;

import fr.tanchou.enums.InstructionType;

import java.util.List;

public class Transaction {
    private final int transactionId;
    private final List<Operation> operations;

    public Transaction(int transactionId, List<Operation> operations) {
        this.transactionId = transactionId;
        this.operations = operations;

        getOperations().add(new Operation(InstructionType.COMMIT, null, null));
    }

    public int getTransactionId() {
        return transactionId;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}

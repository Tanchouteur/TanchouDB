package fr.tanchou.language;

import java.util.List;

public class Transaction {
    private final int transactionId;
    private final List<Operation> operations;

    public Transaction(int transactionId, List<Operation> operations) {
        this.transactionId = transactionId;
        this.operations = operations;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}

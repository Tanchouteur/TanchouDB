package fr.tanchou.tester;

import fr.tanchou.dataInstance.FullBufferData;
import fr.tanchou.language.Transaction;
import fr.tanchou.language.TransactionManager;
import fr.tanchou.utils.TransactionResult;

public class Main {
    public static void main(String[] args) {

        TransactionManager transactionManager = new TransactionManager();

        String select = "SELECT * testDB testTable";

        String createTable = "CREATE TABLE testDB testTable";


        Transaction transaction = transactionManager.createTransaction(select);

        TransactionResult transactionResult = transactionManager.executeTransaction(transaction);

        System.out.println(transactionResult);
    }
}
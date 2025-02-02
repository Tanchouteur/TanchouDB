package fr.tanchou.tester;

import fr.tanchou.dataInstance.FullBufferData;
import fr.tanchou.structure.*;
import fr.tanchou.structure.utils.DbManager;
import fr.tanchou.structure.utils.IDbManager;

public class Main {
    public static void main(String[] args) {

        IDbManager dbManager = new DbManager();

        dbManager.listDatabases();

        //dbManager.createDatabase("testDB");

        Database db = dbManager.getDatabasesMap().get("testDB");

        db.addTable(new Table("testTable"));

        System.out.println(db);

        System.out.println(FullBufferData.getInstance().getTablesIndex());
    }
}
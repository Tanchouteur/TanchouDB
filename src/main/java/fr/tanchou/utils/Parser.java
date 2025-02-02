package fr.tanchou.utils;

import fr.tanchou.structure.DataJson;
import fr.tanchou.structure.Database;
import fr.tanchou.structure.DbNameList;
import fr.tanchou.structure.Table;

public interface Parser <T> {

    Database parseDatabase(T t);
    DbNameList parseDBList(T t);
    DataJson parseData(T t, Table table);

}

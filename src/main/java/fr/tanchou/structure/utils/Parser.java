package fr.tanchou.structure.utils;

import fr.tanchou.structure.Database;
import fr.tanchou.structure.DbNameList;

public interface Parser <T> {

    Database parseDatabase(T t);

    DbNameList parseDBList(T t);

}

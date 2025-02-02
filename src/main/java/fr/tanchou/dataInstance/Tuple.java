package fr.tanchou.dataInstance;

import fr.tanchou.structure.DataJson;

public class Tuple {
    private final String primaryKeyValue;
    private final DataJson dataJson;

    public Tuple(String primaryKeyValue, DataJson dataJson) {
        this.primaryKeyValue = primaryKeyValue;
        this.dataJson = dataJson;
    }

    public String getPrimaryKeyValue() {
        return primaryKeyValue;
    }

    public DataJson getDataJson() {
        return dataJson;
    }
}

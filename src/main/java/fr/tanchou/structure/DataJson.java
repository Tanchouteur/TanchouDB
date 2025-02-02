package fr.tanchou.structure;

import java.util.Map;

public class DataJson implements Data<Map<String, Object>> {
    private final Map<String, Object> data;

    public DataJson(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
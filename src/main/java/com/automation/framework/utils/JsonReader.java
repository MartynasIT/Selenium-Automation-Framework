package com.automation.framework.utils;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonReader {
    String path = null;
    @Getter
    @Setter
    private JSONObject jsonObject;
    JSONParser parser;

    public JsonReader(String path) {
        this.path = path;
        parser = new JSONParser();
        if (path.contains(".json"))
            parseFromFile();
        else
            parseFromString();
    }

    public JsonReader parseFromFile() {
        try {
            setJsonObject((JSONObject) parser.parse(SystemUtil.readFile(path)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException("Problem reading JSON file");
        }
        return this;
    }

    public JsonReader parseFromString() {
        try {
            setJsonObject((JSONObject) parser.parse(path));
        } catch (ParseException e) {
            throw new RuntimeException("Problem reading JSON string");
        }
        return this;
    }

    public JSONObject returnJsonObject() {
        return getJsonObject();
    }

    public String getValue(String key) {
        if (key.contains("."))
            return extractFromJsonArray(key);

        return getJsonObject().get(key).toString();
    }

    private String extractFromJsonArray(String key) {
        String[] parts = key.split("\\.");
        String array = parts[0];
        String item = parts[1];
        JSONObject results = (JSONObject) getJsonObject().get(array);
        return results.get(item).toString();
    }
}

package com.automation.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class JsonReader {
    @Getter @Setter
    private String path;
    @Getter @Setter
    private JSONObject jsonObject;
    JSONParser parser;

    public JsonReader(String path) {
        setPath(path);
        parser = new JSONParser();
        if (getPath().contains(".json"))
            parseFromFile();
        else
            parseFromString();
    }

    public JsonReader parseFromFile() {
        try {
            setJsonObject((JSONObject) parser.parse(SystemUtil.readFile(getPath())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException("Problem reading JSON file");
        }
        return this;
    }

    public JsonReader parseFromString() {
        try {
            setJsonObject((JSONObject) parser.parse(getPath()));
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

    public JSONArray getJsonArray(String value) {
        JSONObject object = parseFromFile().getJsonObject();
        return (JSONArray) object.get(value);
    }

    public JsonNode getJsonMapper(String list) {
        JsonNode mapper = null;
        try {
            mapper = new ObjectMapper().readTree(getJsonArray(list).toJSONString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return mapper;
    }
}

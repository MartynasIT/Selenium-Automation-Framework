package com.automation.framework.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonManipulator {
    String uglyJson;

    public JsonManipulator(String uglyJson) {
        this.uglyJson = uglyJson;
    }

    public String makeJsonNice() {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJson);
        return gson.toJson(je).
                replace("\\n\\t", " ").
                replace("\\", "").
                replace("\\n", "").
                replace("\t", "");
    }
}

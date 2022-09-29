package com.automation.framework.loging;

import com.automation.framework.utils.JsonManipulator;
import com.automation.framework.utils.SystemUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonLogger implements FileLogger {
    JSONObject jsonObject;
    public final static String JSON_PATH = "./Reports/" + "RunResults.json";

    public JsonLogger() {
        CreateResultDir();
    }

    public synchronized void log(String methodName, boolean passed, String error, String browser, String fullName) {
        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(new FileReader(JSON_PATH));
        } catch (IOException | ParseException e) {
            jsonObject = new JSONObject();
        }
        JSONObject inner = new JSONObject();
        inner.put("Method_Name", methodName);
        inner.put("Browser", browser);
        inner.put("Passed", passed);
        inner.put("Error_Log", error);
        jsonObject.put(fullName, inner);
        SystemUtil.writeFile(JSON_PATH, new JsonManipulator(jsonObject.toString()).makeJsonNice());
    }

}

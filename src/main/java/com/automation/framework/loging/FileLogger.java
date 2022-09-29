package com.automation.framework.loging;

import com.automation.framework.utils.SystemUtil;

public interface FileLogger {
    void log(String className, boolean passed, String error, String browser, String fullName);

    default void CreateResultDir() {
        SystemUtil.makeDir("./Reports/");
    }
}

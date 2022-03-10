package com.automation.framework.utils;

import java.util.Arrays;
import java.util.Objects;

public class ArrayWorker {

    public boolean are2DArraysSame(String[][] array1, String[][] array2) {
        return Arrays.deepEquals(array1, array2);
    }

    public String[] cleanArray(String[] array) {
        return Arrays.stream(array).filter(Objects::nonNull).toArray(String[]::new);
    }
}

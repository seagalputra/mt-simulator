package com.infosys.mtsimulator.basesimulator;

import javassist.NotFoundException;

import java.util.Map;
import java.util.regex.Pattern;

public interface BaseSimulator {
    Pattern getPattern(String key);
    Map<String, String> matchPattern(String message, String pattern) throws NotFoundException;
    String replaceValue(String key, String message, String messageReplacement);
    String removeField(String key, String message);
}

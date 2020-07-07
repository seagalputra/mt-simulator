package com.infosys.mtsimulator.domain.simulator.basesimulator;

import com.infosys.mtsimulator.entity.MatchedString;

import java.util.regex.Pattern;

public interface BaseSimulator {
    Pattern getPattern(String key);
    MatchedString matchPattern(String message, String pattern);
    String replaceApplicationHeader(String message);
    String addPrefixCPTY(String key, String message);
    String swapValue(String firstKey, String secondKey, String message);
    String replaceValue(String key, String message, String messageReplacement);
    String removeField(String key, String message);
    String swapField(String key, String message);
    String swapField(String firstKey, String secondKey, String message);
    String removeUnusedField(String key, String message);
}

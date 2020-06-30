package com.infosys.mtsimulator.simulator.basesimulator;

import com.infosys.mtsimulator.entity.MatchedString;
import javassist.NotFoundException;

import java.util.regex.Pattern;

public interface BaseSimulator {
    Pattern getPattern(String key);
    MatchedString matchPattern(String message, String pattern) throws NotFoundException;
    String replaceApplicationHeader(String message);
    String addPrefixCPTY(String key, String message);
    String swapValue(String firstKey, String secondKey, String message) throws NotFoundException;
    String replaceValue(String key, String message, String messageReplacement);
    String removeField(String key, String message);
    String swapField(String key, String message);
    String removeUnusedField(String key, String message);
}
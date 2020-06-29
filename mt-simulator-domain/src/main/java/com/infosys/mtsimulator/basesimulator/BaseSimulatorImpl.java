package com.infosys.mtsimulator.basesimulator;

import javassist.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseSimulatorImpl implements BaseSimulator {
    @Override
    public Pattern getPattern(String key) {
        String pattern = "^" + key + "(.*)$";
        return Pattern.compile(pattern, Pattern.MULTILINE);
    }

    @Override
    public Map<String, String> matchPattern(String message, String pattern) throws NotFoundException {
        Matcher matcher = getPattern(pattern).matcher(message);
        if (!matcher.find()) throw new NotFoundException("No String Found!");

        Map<String, String> result = new HashMap<>();
        result.put("matched", matcher.group());
        result.put("value", matcher.group(1));

        return result;
    }

    @Override
    public String replaceValue(String key, String message, String messageReplacement) {
        return getPattern(key)
                .matcher(message)
                .replaceFirst(messageReplacement);
    }

    @Override
    public String removeField(String key, String message) {
        return message.replaceFirst("^" + key + "(.*)\\n", "");
    }
}

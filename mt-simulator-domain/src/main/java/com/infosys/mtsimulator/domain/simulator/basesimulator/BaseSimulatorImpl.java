package com.infosys.mtsimulator.domain.simulator.basesimulator;

import com.infosys.mtsimulator.entity.MatchedString;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BaseSimulatorImpl implements BaseSimulator {
    @Override
    public Pattern getPattern(String key) {
        String pattern = "^" + key + "(.*)$";
        return Pattern.compile(pattern, Pattern.MULTILINE);
    }

    @Override
    public MatchedString matchPattern(String message, String pattern) throws NotFoundException {
        Matcher matcher = getPattern(pattern).matcher(message);
        if (!matcher.find()) throw new NotFoundException("No String Found!");

        return MatchedString.builder()
                .matched(matcher.group())
                .value(matcher.group(1))
                .build();
    }

    @Override
    public String replaceApplicationHeader(String message) {
        return message.replace("{2:I", "{2:O");
    }

    @Override
    public String addPrefixCPTY(String key, String message) {
        return message.replace(key, ":20:CPTY");
    }

    @Override
    public String swapValue(String firstKey, String secondKey, String message) throws NotFoundException {
        String result = "";
        String matchedFirstPattern = matchPattern(message, firstKey).getValue();
        String matchedSecondPattern = matchPattern(message, secondKey).getValue();
        result = replaceValue(firstKey, message, firstKey + matchedSecondPattern);
        return replaceValue(secondKey, result, secondKey + matchedFirstPattern);
    }

    @Override
    public String replaceValue(String key, String message, String messageReplacement) {
        return getPattern(key)
                .matcher(message)
                .replaceFirst(messageReplacement);
    }

    @Override
    public String removeField(String key, String message) {
        return message.replaceFirst("(?m)^" + key + "(.*)\\n", "");
    }

    @Override
    public String swapField(String key, String message) {
        Matcher fieldMatcher = getPattern(key).matcher(message);
        List<String> listMatched = getAllMatchedString(fieldMatcher);

        return message.replace(listMatched.get(0), "*")
                .replace(listMatched.get(1), listMatched.get(0))
                .replace("*", listMatched.get(1));
    }

    @Override
    public String swapField(String firstKey, String secondKey, String message) throws NotFoundException {
        MatchedString firstMatched = matchPattern(message, firstKey);
        MatchedString secondMatched = matchPattern(message, secondKey);

        return message.replace(firstMatched.getMatched(), "*")
                .replace(secondMatched.getMatched(), firstMatched.getMatched())
                .replace("*", secondMatched.getMatched());
    }

    @Override
    public String removeUnusedField(String key, String message) {
        List<String> listMessage = new ArrayList<>(Arrays.asList(message.split(key)));
        listMessage.remove(listMessage.size() - 1);
        listMessage.add("-}");
        return String.join("", listMessage);
    }

    private List<String> getAllMatchedString(Matcher fieldMatcher) {
        List<String> listMatched = new ArrayList<>();
        while(fieldMatcher.find())
            listMatched.add(fieldMatcher.group());
        return listMatched;
    }
}

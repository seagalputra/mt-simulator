package com.infosys.mtsimulator.basesimulator;

import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BaseSimulatorImplTest {
    private static BaseSimulator baseSimulator;

    @BeforeAll
    public static void setup() {
        baseSimulator = new BaseSimulatorImpl();
    }

    @Test
    public void shouldHaveReplacedValueInMoreLineMessage() {
        String replacedMessage = baseSimulator.replaceValue(":8T:", ":20:1234\n:8T:45AB\n:12D:788SB,\n",":8T:1234");
        assertEquals(":20:1234\n:8T:1234\n:12D:788SB,\n", replacedMessage);
    }

    @Test
    public void shouldHaveRemovedSpecificLineInTheMessage() {
        String removedMessage = baseSimulator.removeField(":20:", ":20:1234\n:8T:1234\n:12D:78839,\n");
        assertEquals(":8T:1234\n:12D:78839,\n", removedMessage);
    }

    @Test
    public void shouldReturnMatchedStringAndValueWhenMatchingPattern() {
        String pattern = baseSimulator
                .getPattern(":20:")
                .pattern();

        Map<String, String> matchedString = new HashMap<>();
        try {
            matchedString = baseSimulator.matchPattern(":20:1234\n:8T:ACVD\n:12D:78839,\n", pattern);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(":20:1234", matchedString.get("matched"));
        assertEquals("1234", matchedString.get("value"));
    }
}
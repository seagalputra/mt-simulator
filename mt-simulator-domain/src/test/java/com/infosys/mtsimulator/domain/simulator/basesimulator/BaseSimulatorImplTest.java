package com.infosys.mtsimulator.domain.simulator.basesimulator;

import com.infosys.mtsimulator.api.exception.NotFoundException;
import com.infosys.mtsimulator.domain.DomainConfiguration;
import com.infosys.mtsimulator.entity.MatchedString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DomainConfiguration.class)
class BaseSimulatorImplTest {

    @Autowired
    private BaseSimulator baseSimulator;

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

        MatchedString matchedString = baseSimulator.matchPattern(":20:1234\n:8T:ACVD\n:12D:78839,\n", pattern);

        assertEquals(":20:1234", matchedString.getMatched());
        assertEquals("1234", matchedString.getValue());
    }

    @Test
    public void shouldThrowErrorWhenStringNotFoundInMessage() {
        String pattern = baseSimulator
                .getPattern(":21")
                .pattern();

        Assertions.assertThrows(NotFoundException.class, () -> {
           baseSimulator.matchPattern(":20:1234\n:8T:ACVD\n:12D:78839,\n", pattern);
        });
    }

    @Test
    public void shouldReplaceApplicationHeader() {
        String replacedApplicationHeader = baseSimulator.replaceApplicationHeader("{1:F01CENAIDJAAXXX0000000000}{2:I3001234556XXXXXN}");
        assertEquals("{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}", replacedApplicationHeader);
    }

    @Test
    public void shouldAddCPTYPrefixInSpecificMessageKey() {
        String addedPrefix = baseSimulator.addPrefixCPTY(":20:", ":15A:\n:20:6669-1\n:22A:NEWT\n:22C:9625CENAJA\n");
        assertEquals(":15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n", addedPrefix);
    }

    @Test
    public void shouldReturnSwappedValueMessageWithSpecificKey() {
        String swappedValue = baseSimulator.swapValue(":82A:", ":87A:", ":82A:CENAIDJA\n:87A:1234556");
        assertEquals(":82A:1234556\n:87A:CENAIDJA", swappedValue);
    }

    @Test
    public void shouldReturnSwappedFieldWithSpecificKey() {
        String swappedField = baseSimulator.swapField(":57A:", ":32B:IDR11559625000,\n:57A:CENAIDJA\n:33B:EUR1000000,\n:57A:1234556\n");
        assertEquals(":32B:IDR11559625000,\n:57A:1234556\n:33B:EUR1000000,\n:57A:CENAIDJA\n", swappedField);
    }

    @Test
    public void shouldReturnRemovedUnusedField() {
        String removedField = baseSimulator.removeUnusedField(":58A:", "{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:N\n:15B:\n:30T:20200203\n:30V:20200205\n:36:11559,625\n:32B:IDR11559625000,\n:57A:1234556\n:33B:EUR1000000,\n:57A:CENAIDJA\n:58A:1234556\n:15E:\n:35B:\n:17Y:F\n:17Z:Y\n:22Q:ALP-INV-FVOCI-S\n:17Q:Y\n-}");
        assertEquals(
                "{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:N\n:15B:\n:30T:20200203\n:30V:20200205\n:36:11559,625\n:32B:IDR11559625000,\n:57A:1234556\n:33B:EUR1000000,\n:57A:CENAIDJA\n-}",
                removedField
        );
    }

    @Test
    public void shouldReturnSwappedFieldInMessageWithTwoKeys() {
        String swappedField = baseSimulator.swapField(":57J:", ":57A:", ":32B:USD1000000,\n:57J:/NOSI/NETS\n:33B:IDR17076000000,\n:57A:1234556");
        assertEquals(":32B:USD1000000,\n:57A:1234556\n:33B:IDR17076000000,\n:57J:/NOSI/NETS", swappedField);
    }
}
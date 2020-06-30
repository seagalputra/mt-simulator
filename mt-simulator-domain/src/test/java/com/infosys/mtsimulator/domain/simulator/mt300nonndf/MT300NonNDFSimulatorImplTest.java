package com.infosys.mtsimulator.domain.simulator.mt300nonndf;

import com.infosys.mtsimulator.domain.DomainConfiguration;
import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import com.infosys.mtsimulator.domain.constant.MTSimulatorConstant;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DomainConfiguration.class)
class MT300NonNDFSimulatorImplTest {

    @Autowired
    private SimulatorStrategy mt300NonNDFSimulator;
    private static String inputMessage;

    @BeforeAll
    public static void setup() {
        inputMessage = "{1:F01CENAIDJAAXXX0000000000}{2:I3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:CENAIDJA\n:87A:1234556\n:17F:N\n:15B:\n:30T:20200203\n:30V:20200205\n:36:11559,625\n:32B:EUR1000000,\n:57A:CENAIDJA\n:33B:IDR11559625000,\n:57A:1234556\n:58A:1234556\n:15E:\n:35B:\n:17Y:F\n:17Z:Y\n:22Q:ALP-INV-FVOCI-S\n:17Q:Y\n-}";
    }

    @Test
    public void shouldReturnConvertedStringFromInputWithTypeAutoMatch() {
        String expectedMessage = "{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:N\n:15B:\n:30T:20200203\n:30V:20200205\n:36:11559,625\n:32B:IDR11559625000,\n:57A:1234556\n:33B:EUR1000000,\n:57A:CENAIDJA\n-}";
        String resultMessage = "";
        try {
            resultMessage = mt300NonNDFSimulator.parse(inputMessage, MTSimulatorConstant.AM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void shouldReturnConvertedStringFromInputWithTypePartialMatch() {
        String expectedMessage = "{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:N\n:15B:\n:30T:20200203\n:30V:20200205\n:36:11559,625\n:32B:IDR11559625000,\n:57A:CENAIDJA\n:33B:EUR1000000,\n:57A:1234556\n-}";
        String resultMessage = "";
        try {
            resultMessage = mt300NonNDFSimulator.parse(inputMessage, MTSimulatorConstant.PM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expectedMessage, resultMessage);
    }

    @Test
    public void shouldReturnConvertedStringFromInputWithTypeUnMatch() {
        String expectedMessage = "{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:N\n:15B:\n:30T:20191225\n:30V:20200101\n:36:11559,625\n:32B:IDR11559625000,\n:57A:1234556\n:33B:EUR1000000,\n:57A:CENAIDJA\n-}";
        String resultMessage = "";
        try {
            resultMessage = mt300NonNDFSimulator.parse(inputMessage, MTSimulatorConstant.UM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expectedMessage, resultMessage);
    }
}
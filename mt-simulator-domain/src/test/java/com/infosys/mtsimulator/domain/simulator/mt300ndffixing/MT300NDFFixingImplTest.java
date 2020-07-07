package com.infosys.mtsimulator.domain.simulator.mt300ndffixing;

import com.infosys.mtsimulator.domain.DomainConfiguration;
import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DomainConfiguration.class)
class MT300NDFFixingImplTest {

    @Qualifier("MT300NDFFixingImpl")
    @Autowired
    private SimulatorStrategy simulatorStrategy;
    private static String inputMessage;

    @BeforeAll
    public static void setup() {
        inputMessage = "{1:F01CENAIDJAAXXX0000000000}{2:I3001234556XXXXXN}{3:{108:2034}}{4:\n:15A:\n:20:2034-2\n:22A:NEWT\n:22C:7076CENAJA\n:17I:Y\n:82A:CENAIDJA\n:87A:1234556\n:17F:Y\n:17O:Y\n:21A:2034-1\n:15B:\n:30T:20200130\n:30V:20200203\n:36:17076,\n:32B:USD1000000,\n:57J:/NOSI/NETS\n:33B:IDR17076000000,\n:57A:1234556\n:58A:1234556\n:15E:\n:22U:FXNDFO\n:35B:\n:17Y:F\n:17Z:Y\n:22Q:FX-MT-NDF\n:17Q:Y\n-}";
    }

    @Test
    public void shouldReturnParsedMessageWithInputAndTypeAutoMatch() {
        String expectedResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:2034}}{4:\n:15A:\n:20:CPTY2034-2\n:22A:NEWT\n:22C:7076CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:Y\n:17O:Y\n:20:CPTY2034-1\n:15B:\n:30T:20200130\n:30V:20200203\n:36:17076,\n:32B:IDR17076000000,\n:57A:1234556\n:33B:USD1000000,\n:57J:/NOSI/NETS\n-}";
        String actualResult = simulatorStrategy.parse(inputMessage, AM);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnParsedMessageWithInputAndTypePartialMatch() {
        String expectedResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:2034}}{4:\n:15A:\n:20:CPTY2034-2\n:22A:NEWT\n:22C:7076CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:Y\n:17O:Y\n:20:CPTY2034-1\n:15B:\n:30T:20200130\n:30V:20200203\n:36:17076,\n:32B:IDR17076000000,\n:57A:1234556\n:33B:USD1000000,\n:57J:/NOSI/NETS\n-}";
        String actualResult = simulatorStrategy.parse(inputMessage, PM);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnParsedMessageWithInputAndTypeUnMatch() {
        String expectedResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:2034}}{4:\n:15A:\n:20:CPTY2034-2\n:22A:NEWT\n:22C:7076CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:Y\n:17O:Y\n:20:CPTY2034-1\n:15B:\n:30T:20200130\n:30V:20200203\n:36:17076,\n:32B:IDR17076000000,\n:57A:1234556\n:33B:USD1000000,\n:57J:/NOSI/NETS\n-}";
        String actualResult = simulatorStrategy.parse(inputMessage, UM);

        assertEquals(expectedResult, actualResult);
    }
}
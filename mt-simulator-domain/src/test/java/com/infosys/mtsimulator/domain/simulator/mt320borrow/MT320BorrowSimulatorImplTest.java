package com.infosys.mtsimulator.domain.simulator.mt320borrow;

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
class MT320BorrowSimulatorImplTest {

    @Qualifier("MT320BorrowSimulatorImpl")
    @Autowired
    private SimulatorStrategy simulatorStrategy;
    private static String inputMessage;

    @BeforeAll
    public static void setup() {
        inputMessage = "{1:F01CENAIDJAAXXX0000000000}{2:I320BBIJIDJAXXXXN}{3:{108:2346}}{4:\n:15A:\n:20:2346-1\n:22A:NEWT\n:94A:BILA\n:22B:CONF\n:22C:BBIJJA0006CENAJA\n:82A:CENAIDJA\n:87A:BBIJIDJAXXX\n:15B:\n:17R:B\n:30T:20200131\n:30V:20200204\n:30P:20200304\n:32B:IDR100000000,\n:30X:20200304\n:34E:IDR666194,45\n:37G:8,27000006\n:14D:ACT/360\n:15C:\n:57A:BBIJIDJAXXX\n:58A:BBIJIDJAXXX\n:15D:\n:57A:CENAIDJA\n-}";
    }

    @Test
    public void shouldReturnParsedMessageWithInputInAutoMatch() {
        String expectedResult = "{1:F01CENAIDJAAXXX0000000000}{2:O320BBIJIDJAXXXXN}{3:{108:2346}}{4:\n:15A:\n:20:CPTY2346-1\n:22A:NEWT\n:94A:BILA\n:22B:CONF\n:22C:BBIJJA0006CENAJA\n:82A:BBIJIDJAXXX\n:87A:CENAIDJA\n:15B:\n:17R:L\n:30T:20200131\n:30V:20200204\n:30P:20200304\n:32B:IDR100000000,\n:30X:20200304\n:34E:NIDR666194,45\n:37G:8,27000006\n:14D:ACT/360\n:15C:\n:57A:CENAIDJA\n:15D:\n:57A:BBIJIDJAXXX\n-}";
        String actualResult = simulatorStrategy.parse(inputMessage, AM);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnParsedMessageWithInputInPartialMatch() {
        String expectedResult = "{1:F01CENAIDJAAXXX0000000000}{2:O320BBIJIDJAXXXXN}{3:{108:2346}}{4:\n:15A:\n:20:CPTY2346-1\n:22A:NEWT\n:94A:BILA\n:22B:CONF\n:22C:BBIJJA0006CENAJA\n:82A:BBIJIDJAXXX\n:87A:CENAIDJA\n:15B:\n:17R:L\n:30T:20191225\n:30V:20200204\n:30P:20200304\n:32B:IDR100000000,\n:30X:20200304\n:34E:NIDR666194,45\n:37G:8,27000006\n:14D:ACT/360\n:15C:\n:57A:CENAIDJA\n:15D:\n:57A:BBIJIDJAXXX\n-}";
        String actualResult = simulatorStrategy.parse(inputMessage, PM);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnParsedMessageWithInputInUnMatch() {
        String expectedResult = "{1:F01CENAIDJAAXXX0000000000}{2:O320BBIJIDJAXXXXN}{3:{108:2346}}{4:\n:15A:\n:20:CPTY2346-1\n:22A:NEWT\n:94A:BILA\n:22B:CONF\n:22C:BBIJJA0006CENAJA\n:82A:BBIJIDJAXXX\n:87A:CENAIDJA\n:15B:\n:17R:L\n:30T:20191225\n:30V:20191225\n:30P:20200101\n:32B:IDR100000000,\n:30X:20200304\n:34E:NIDR666194,45\n:37G:8,27000006\n:14D:ACT/360\n:15C:\n:57A:CENAIDJA\n:15D:\n:57A:BBIJIDJAXXX\n-}";
        String actualResult = simulatorStrategy.parse(inputMessage, UM);

        assertEquals(expectedResult, actualResult);
    }
}
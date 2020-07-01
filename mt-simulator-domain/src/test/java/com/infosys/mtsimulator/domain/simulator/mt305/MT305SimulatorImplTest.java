package com.infosys.mtsimulator.domain.simulator.mt305;

import com.infosys.mtsimulator.domain.DomainConfiguration;
import com.infosys.mtsimulator.domain.simulator.SimulatorStrategy;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DomainConfiguration.class)
class MT305SimulatorImplTest {

    @Qualifier("MT305SimulatorImpl")
    @Autowired
    private SimulatorStrategy mt305Simulator;
    private static String buyMessage;
    private static String sellMessage;

    @BeforeAll
    public static void setup() {
        buyMessage = "{1:F01CENAIDJAAXXX0000000000}{2:I3051234556XXXXXN}{3:{108:4212}}{4:\n:15A:\n:20:4212-1\n:21:NEW\n:22:NEW/0014CENAJA\n:23:BUY/CALL/E/USD\n:82A:CENAIDJA\n:87A:1234556\n:30:200203\n:31G:200331/1500/JPTO\n:31E:200402\n:26F:PRINCIPAL\n:32B:USD2000000,\n:36:14000,\n:33B:IDR28000000000,\n:37K:PCT21,25\n:34P:200203USD424912,84\n:53A:/888888888\nCENAIDJA\n:57A:1234556\n:22U:FXVAOP\n:35B:\n:17Y:F\n:17Z:Y\n:22Q:FXO-FVTPL-OP1\n:17Q:Y\n-}";
        sellMessage = "{1:F01CENAIDJAAXXX0000000000}{2:I3051234556XXXXXN}{3:{108:7261}}{4:\n:15A:\n:20:7261-1\n:21:NEW\n:22:NEW/0014CENAJA\n:23:SELL/PUT/E/USD\n:82A:CENAIDJA\n:87A:1234556\n:30:200203\n:31G:200330/1500/JPTO\n:31E:200402\n:26F:PRINCIPAL\n:32B:USD2000000,\n:36:14000,\n:33B:IDR28000000000,\n:37K:PCT0,\n:34R:200203USD92,35\n:57A:/888888888\nCENAIDJA\nFXVAOP\nF\nY\nFXO-FVTPL-OP1\nY\n-}";
    }

    @Test
    public void shouldReturnParsedMessageWithBuyMessageInputInAutoMatch() {
        String expectedBuyResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3051234556XXXXXN}{3:{108:4212}}{4:\n:15A:\n:20:CPTY4212-1\n:21:NEW\n:22:NEW/0014CENAJA\n:23:SELL/CALL/E/USD\n:82A:1234556\n:87A:CENAIDJA\n:30:200203\n:31G:200331/1500/JPTO\n:31E:200402\n:26F:PRINCIPAL\n:32B:USD2000000,\n:36:14000,\n:33B:IDR28000000000,\n:37K:PCT21,25\n:34R:200203USD424912,84\n:57A:1234556\n-}";
        String actualBuyResult = "";
        try {
            actualBuyResult = mt305Simulator.parse(buyMessage, AM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expectedBuyResult, actualBuyResult);
    }

    @Test
    public void shouldReturnParsedMessageWithBuyMessageInputInPartialMatch() {
        String expectedBuyResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3051234556XXXXXN}{3:{108:4212}}{4:\n:15A:\n:20:CPTY4212-1\n:21:NEW\n:22:NEW/0014CENAJA\n:23:SELL/CALL/E/USD\n:82A:1234556\n:87A:CENAIDJA\n:30:20191225\n:31G:200331/1500/JPTO\n:31E:200402\n:26F:PRINCIPAL\n:32B:USD2000000,\n:36:14000,\n:33B:IDR28000000000,\n:37K:PCT21,25\n:34R:200203USD424912,84\n:57A:1234556\n-}";
        String actualBuyResult = "";
        try {
            actualBuyResult = mt305Simulator.parse(buyMessage, PM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expectedBuyResult, actualBuyResult);
    }

    @Test
    public void shouldReturnParsedMessageWithBuyMessageInputInUnMatch() {
        String expectedBuyResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3051234556XXXXXN}{3:{108:4212}}{4:\n:15A:\n:20:CPTY4212-1\n:21:NEW\n:22:NEW/0014CENAJA\n:23:SELL/CALL/E/USD\n:82A:1234556\n:87A:CENAIDJA\n:30:20191225\n:31G:200331/1500/JPTO\n:31E:20200101\n:26F:PRINCIPAL\n:32B:USD2000000,\n:36:14000,\n:33B:IDR28000000000,\n:37K:PCT21,25\n:34R:200203USD424912,84\n:57A:1234556\n-}";
        String actualBuyResult = "";
        try {
            actualBuyResult = mt305Simulator.parse(buyMessage, UM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expectedBuyResult, actualBuyResult);
    }

    @Test
    public void shouldReturnParsedMessageWithSellMessageInputInAutoMatch() {
        String expectedSellResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3051234556XXXXXN}{3:{108:7261}}{4:\n:15A:\n:20:CPTY7261-1\n:21:NEW\n:22:NEW/0014CENAJA\n:23:BUY/PUT/E/USD\n:82A:1234556\n:87A:CENAIDJA\n:30:200203\n:31G:200330/1500/JPTO\n:31E:200402\n:26F:PRINCIPAL\n:32B:USD2000000,\n:36:14000,\n:33B:IDR28000000000,\n:37K:PCT0,\n:34P:200203USD92,35\n:57A:/888888888\nCENAIDJA\nFXVAOP\nF\nY\nFXO-FVTPL-OP1\nY\n-}";
        String actualSellResult = "";
        try {
            actualSellResult = mt305Simulator.parse(sellMessage, AM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expectedSellResult, actualSellResult);
    }

    @Test
    public void shouldReturnParsedMessageWithSellMessageInputInPartialMatch() {
        String expectedSellResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3051234556XXXXXN}{3:{108:7261}}{4:\n:15A:\n:20:CPTY7261-1\n:21:NEW\n:22:NEW/0014CENAJA\n:23:BUY/PUT/E/USD\n:82A:1234556\n:87A:CENAIDJA\n:30:20191225\n:31G:200330/1500/JPTO\n:31E:200402\n:26F:PRINCIPAL\n:32B:USD2000000,\n:36:14000,\n:33B:IDR28000000000,\n:37K:PCT0,\n:34P:200203USD92,35\n:57A:/888888888\nCENAIDJA\nFXVAOP\nF\nY\nFXO-FVTPL-OP1\nY\n-}";
        String actualSellResult = "";
        try {
            actualSellResult = mt305Simulator.parse(sellMessage, PM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expectedSellResult, actualSellResult);
    }

    @Test
    public void shouldReturnParsedMessageWithSellMessageInputInUnMatch() {
        String expectedSellResult = "{1:F01CENAIDJAAXXX0000000000}{2:O3051234556XXXXXN}{3:{108:7261}}{4:\n:15A:\n:20:CPTY7261-1\n:21:NEW\n:22:NEW/0014CENAJA\n:23:BUY/PUT/E/USD\n:82A:1234556\n:87A:CENAIDJA\n:30:20191225\n:31G:200330/1500/JPTO\n:31E:20200101\n:26F:PRINCIPAL\n:32B:USD2000000,\n:36:14000,\n:33B:IDR28000000000,\n:37K:PCT0,\n:34P:200203USD92,35\n:57A:/888888888\nCENAIDJA\nFXVAOP\nF\nY\nFXO-FVTPL-OP1\nY\n-}";
        String actualSellResult = "";
        try {
            actualSellResult = mt305Simulator.parse(sellMessage, UM);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        assertEquals(expectedSellResult, actualSellResult);
    }
}
package com.infosys.mtsimulator.domain.service;

import com.infosys.mtsimulator.domain.DomainConfiguration;
import com.infosys.mtsimulator.api.request.ParseMessageRequest;
import com.infosys.mtsimulator.api.response.ParseMessageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.infosys.mtsimulator.domain.constant.MTSimulatorConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DomainConfiguration.class)
class SimulatorServiceImplTest {

    @Autowired
    private SimulatorService simulatorService;

    @Test
    public void shouldReturnParsedResponseMessageWhenMessageIsInputted() {
        ParseMessageRequest request = ParseMessageRequest.builder()
                .simulatorMessage("{1:F01CENAIDJAAXXX0000000000}{2:I3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:CENAIDJA\n:87A:1234556\n:17F:N\n:15B:\n:30T:20200203\n:30V:20200205\n:36:11559,625\n:32B:EUR1000000,\n:57A:CENAIDJA\n:33B:IDR11559625000,\n:57A:1234556\n:58A:1234556\n:15E:\n:35B:\n:17Y:F\n:17Z:Y\n:22Q:ALP-INV-FVOCI-S\n:17Q:Y\n-}")
                .simulatorType(MT300_NON_NDF)
                .build();

        ParseMessageResponse expectedResponse = ParseMessageResponse.builder()
                .autoMatchMessage("{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:N\n:15B:\n:30T:20200203\n:30V:20200205\n:36:11559,625\n:32B:IDR11559625000,\n:57A:1234556\n:33B:EUR1000000,\n:57A:CENAIDJA\n-}")
                .partialMatchMessage("{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:N\n:15B:\n:30T:20200203\n:30V:20200205\n:36:11559,625\n:32B:IDR11559625000,\n:57A:CENAIDJA\n:33B:EUR1000000,\n:57A:1234556\n-}")
                .unMatchMessage("{1:F01CENAIDJAAXXX0000000000}{2:O3001234556XXXXXN}{3:{108:6669}}{4:\n:15A:\n:20:CPTY6669-1\n:22A:NEWT\n:22C:9625CENAJA\n:17I:Y\n:82A:1234556\n:87A:CENAIDJA\n:17F:N\n:15B:\n:30T:20191225\n:30V:20200101\n:36:11559,625\n:32B:IDR11559625000,\n:57A:1234556\n:33B:EUR1000000,\n:57A:CENAIDJA\n-}")
                .build();

        ParseMessageResponse response = simulatorService.parseMessage(request);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void shouldReturnParsedMessageForMT320LendSimulator() {
        ParseMessageRequest request = ParseMessageRequest.builder()
                .simulatorMessage("{1:F01CENAIDJAAXXX0000000000}{2:I320BBIJIDJAXXXXN}{3:{108:2345}}{4:\n:15A:\n:20:2345-1\n:22A:NEWT\n:94A:BILA\n:22B:CONF\n:22C:BBIJJA0827CENAJA\n:82A:CENAIDJA\n:87A:BBIJIDJAXXX\n:15B:\n:17R:L\n:30T:20200131\n:30V:20200204\n:30P:20200304\n:32B:IDR100000000,\n:30X:20200304\n:34E:NIDR666194,44\n:37G:8,27\n:14D:ACT/360\n:15C:\n:57A:BBIJIDJAXXX\n:58A:BBIJIDJAXXX\n:15D:\n:57A:CENAIDJA\n-}")
                .simulatorType(MT320LEND)
                .build();

        ParseMessageResponse expectedResponse = ParseMessageResponse.builder()
                .autoMatchMessage("{1:F01CENAIDJAAXXX0000000000}{2:O320BBIJIDJAXXXXN}{3:{108:2345}}{4:\n:15A:\n:20:CPTY2345-1\n:22A:NEWT\n:94A:BILA\n:22B:CONF\n:22C:BBIJJA0827CENAJA\n:82A:BBIJIDJAXXX\n:87A:CENAIDJA\n:15B:\n:17R:B\n:30T:20200131\n:30V:20200204\n:30P:20200304\n:32B:IDR100000000,\n:30X:20200304\n:34E:IDR666194,44\n:37G:8,27\n:14D:ACT/360\n:15C:\n:57A:CENAIDJA\n:15D:\n:57A:BBIJIDJAXXX\n-}")
                .partialMatchMessage("{1:F01CENAIDJAAXXX0000000000}{2:O320BBIJIDJAXXXXN}{3:{108:2345}}{4:\n:15A:\n:20:CPTY2345-1\n:22A:NEWT\n:94A:BILA\n:22B:CONF\n:22C:BBIJJA0827CENAJA\n:82A:BBIJIDJAXXX\n:87A:CENAIDJA\n:15B:\n:17R:B\n:30T:20191225\n:30V:20200204\n:30P:20200304\n:32B:IDR100000000,\n:30X:20200304\n:34E:IDR666194,44\n:37G:8,27\n:14D:ACT/360\n:15C:\n:57A:CENAIDJA\n:15D:\n:57A:BBIJIDJAXXX\n-}")
                .unMatchMessage("{1:F01CENAIDJAAXXX0000000000}{2:O320BBIJIDJAXXXXN}{3:{108:2345}}{4:\n:15A:\n:20:CPTY2345-1\n:22A:NEWT\n:94A:BILA\n:22B:CONF\n:22C:BBIJJA0827CENAJA\n:82A:BBIJIDJAXXX\n:87A:CENAIDJA\n:15B:\n:17R:B\n:30T:20191225\n:30V:20191225\n:30P:20200101\n:32B:IDR100000000,\n:30X:20200304\n:34E:IDR666194,44\n:37G:8,27\n:14D:ACT/360\n:15C:\n:57A:CENAIDJA\n:15D:\n:57A:BBIJIDJAXXX\n-}")
                .build();

        ParseMessageResponse response = simulatorService.parseMessage(request);

        assertEquals(expectedResponse, response);
    }
}
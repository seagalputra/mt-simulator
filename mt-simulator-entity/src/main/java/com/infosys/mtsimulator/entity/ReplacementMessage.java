package com.infosys.mtsimulator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplacementMessage {
    private String key;
    private String message;
    private String messageReplacement;
}

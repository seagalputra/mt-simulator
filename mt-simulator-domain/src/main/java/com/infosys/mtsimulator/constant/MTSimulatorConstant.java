package com.infosys.mtsimulator.constant;

public class MTSimulatorConstant {
    public static String AM = "AM";
    public static String PM = "PM";
    public static String UM = "UM";

    public static boolean isAutoMatchType(String type) {
        return type.equals(MTSimulatorConstant.AM);
    }

    public static boolean isPartialMatch(String type) {
        return type.equals(MTSimulatorConstant.PM);
    }

    public static boolean isUnMatchType(String type) {
        return type.equals(MTSimulatorConstant.UM);
    }
}

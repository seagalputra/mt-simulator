package com.infosys.mtsimulator.domain.constant;

public class MTSimulatorConstant {
    public final static String AM = "AM";
    public final static String PM = "PM";
    public final static String UM = "UM";

    public final static String MT300_NON_NDF = "MT300NONNDF";
    public final static String MT300_NDF_OPENING = "MT300NDFOPENING";
    public final static String MT300_NDF_FIXING = "MT300NDFFIXING";
    public final static String MT305 = "MT305";
    public final static String MT320BORROW = "MT320BORROW";
    public final static String MT320LEND = "MT320LEND";

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

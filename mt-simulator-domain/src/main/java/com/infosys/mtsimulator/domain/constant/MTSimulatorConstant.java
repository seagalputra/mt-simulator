package com.infosys.mtsimulator.domain.constant;

public class MTSimulatorConstant {
    public static final String AM = "AM";
    public static final String PM = "PM";
    public static final String UM = "UM";

    public static final String MT300_NON_NDF = "MT300NONNDF";
    public static final String MT300_NDF_OPENING = "MT300NDFOPENING";
    public static final String MT300_NDF_FIXING = "MT300NDFFIXING";
    public static final String MT305 = "MT305";
    public static final String MT320BORROW = "MT320BORROW";
    public static final String MT320LEND = "MT320LEND";

    public static final String BUY_TAGS = ":23:BUY";
    public static final String SELL_TAGS = ":23:SELL" ;

    public static boolean isAutoMatchType(String type) {
        return type.equals(MTSimulatorConstant.AM);
    }

    public static boolean isPartialMatch(String type) {
        return type.equals(MTSimulatorConstant.PM);
    }

    public static boolean isUnMatchType(String type) {
        return type.equals(MTSimulatorConstant.UM);
    }

    private MTSimulatorConstant() {
        throw new IllegalStateException("Constant class, do not instantiate it!");
    }
}

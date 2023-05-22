package com.modernmt.model;

public class BillingPeriod extends Model {

    private String begin;
    private String end;
    private long chars;
    private String plan;
    private String planDescription;
    private boolean planForCatTool;
    private float amount;
    private String currency;
    private String currencySymbol;

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public long getChars() {
        return chars;
    }

    public String getPlan() {
        return plan;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public boolean isPlanForCatTool() {
        return planForCatTool;
    }

    public float getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }
}

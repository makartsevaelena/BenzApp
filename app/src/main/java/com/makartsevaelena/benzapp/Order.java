package com.makartsevaelena.benzapp;

public class Order {
    private String gazolineType, terminalCount;
    private double startPrice, finalPrice;
    private int gazolinaValue, orderId;

    public Order(String gazolineType, String terminalCount, double startPrice, int gazolinaValue, double finalPrice) {
        this.gazolineType = gazolineType;
        this.terminalCount = terminalCount;
        this.startPrice = startPrice;
        this.gazolinaValue = gazolinaValue;
        this.finalPrice = finalPrice;
    }

    public Order(String gazolineType, String terminalCount, double startPrice, int gazolinaValue) {
        this.gazolineType = gazolineType;
        this.terminalCount = terminalCount;
        this.startPrice = startPrice;
        this.gazolinaValue = gazolinaValue;
        this.finalPrice = startPrice * gazolinaValue;
    }

    public Order() {

    }

    public int getGazolinaValue() {
        return gazolinaValue;
    }

    public void setGazolinaValue(int gazolinaValue) {
        this.gazolinaValue = gazolinaValue;
    }

    public String getGazolineType() {
        return gazolineType;
    }

    public void setGazolineType(String gazolineType) {
        this.gazolineType = gazolineType;
    }

    public String getTerminalCount() {
        return terminalCount;
    }

    public void setTerminalCount(String terminalCount) {
        this.terminalCount = terminalCount;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

}

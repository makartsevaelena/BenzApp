package com.makartsevaelena.benzapp;

public class Order {
    private String gazolineType, terminalCount, currency = "rub";
    private double priceForLiter, sumPrice;
    private int gazolinaValue, orderId = 123456;

    public Order(int orderId,String gazolineType, String terminalCount, double priceForLiter, int gazolinaValue, double sumPrice) {
        this.orderId = orderId;
        this.gazolineType = gazolineType;
        this.terminalCount = terminalCount;
        this.priceForLiter = priceForLiter;
        this.gazolinaValue = gazolinaValue;
        this.sumPrice = sumPrice;
    }

    public Order(String gazolineType, String terminalCount, double priceForLiter, int gazolinaValue) {
        this.gazolineType = gazolineType;
        this.terminalCount = terminalCount;
        this.priceForLiter = priceForLiter;
        this.gazolinaValue = gazolinaValue;
        this.sumPrice = priceForLiter * gazolinaValue;
    }
    public Order(){
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

    public double getPriceForLiter() {
        return priceForLiter;
    }

    public void setPriceForLiter(double priceForLiter) {
        this.priceForLiter = priceForLiter;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

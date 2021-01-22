package com.makartsevaelena.benzapp;

public class GazolineType {
    private String gazoliveType,currency;
    private double priceForLiter;

    public GazolineType(String gazoliveType, double priceForLiter) {
        this.gazoliveType = gazoliveType;
        this.priceForLiter = priceForLiter;
    }

    public String getGazoliveType() {
        return gazoliveType;
    }

    public void setGazoliveType(String gazoliveType) {
        this.gazoliveType = gazoliveType;
    }

    public double getPriceForLiter() {
        return priceForLiter;
    }

    public void setPriceForLiter(float priceForLiter) {
        this.priceForLiter = priceForLiter;
    }
}

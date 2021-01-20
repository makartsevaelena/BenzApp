package com.makartsevaelena.benzapp;

import java.util.ArrayList;

public class FillingStation {
    ArrayList<String> listTerminalCounts;
    ArrayList<GazolineType> listGazolineTypes;

    public FillingStation(ArrayList<String> listTerminalCounts, ArrayList<GazolineType> listGazolineTypes) {
        this.listTerminalCounts = listTerminalCounts;
        this.listGazolineTypes = listGazolineTypes;
    }

    public FillingStation() {
        listGazolineTypes = new ArrayList<>();
        GazolineType gazolineType1 = new GazolineType("АИ-80", 25);
        GazolineType gazolineType2 = new GazolineType("АИ-92", 14);
        GazolineType gazolineType3 = new GazolineType("АИ-95", 65);
        GazolineType gazolineType4 = new GazolineType("АИ-95+", 10);
        GazolineType gazolineType5 = new GazolineType("АИ-98", 12);
        GazolineType gazolineType6 = new GazolineType("АИ-100", 14.9);
        listGazolineTypes.add(gazolineType1);
        listGazolineTypes.add(gazolineType2);
        listGazolineTypes.add(gazolineType3);
        listGazolineTypes.add(gazolineType4);
        listGazolineTypes.add(gazolineType5);
        listGazolineTypes.add(gazolineType6);
        listTerminalCounts = new ArrayList<>();
        int terminalCounts = 7;
        for (int i = 1; i < terminalCounts; i++) {
            listTerminalCounts.add(String.valueOf(i));
        }
    }

    public ArrayList<String> getTerminalCounts() {
        return listTerminalCounts;
    }

    public void setTerminalCounts(ArrayList<String> terminalCounts) {
        this.listTerminalCounts = terminalCounts;
    }

    public ArrayList<GazolineType> getGazolineTypes() {
        return listGazolineTypes;
    }

    public void setGazolineTypes(ArrayList<GazolineType> gazolineTypes) {
        this.listGazolineTypes = gazolineTypes;
    }
}

package com.farmani.xfilemanager;

public enum ViewType {
    ROW(0), GRID(1);

    private int value;

    ViewType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

package com.example.ewdj_ep3.enums;

public enum Outcome {
    HOME_VICTORY("Home Victory"),
    AWAY_VICTORY("Away Victory"),
    DRAW("Draw"),

    SCHEDULED("Scheduled");

    private final String displayText;

    Outcome(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
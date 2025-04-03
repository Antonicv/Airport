package com.example.AirportApp.model;

public enum CrewPosition {
    PILOT("Piloto"),
    COPILOT("Copiloto"),
    FLIGHT_ATTENDANT("Azafata/o"),
    CHIEF_FLIGHT_ATTENDANT("Supervisor de cabina");

    private final String displayName;

    CrewPosition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
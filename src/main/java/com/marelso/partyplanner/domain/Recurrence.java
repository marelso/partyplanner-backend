package com.marelso.partyplanner.domain;

public enum Recurrence {
    ONETIME("One-time"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    private final String recurrence;

    Recurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getRecurrence() {
        return recurrence;
    }
}
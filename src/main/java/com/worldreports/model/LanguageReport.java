package com.worldreports.model;

public class LanguageReport {

    private final String language;
    private final long speakers;
    private final double worldPercentage;

    public LanguageReport(
            String language,
            long speakers,
            double worldPercentage
    ) {
        this.language = language;
        this.speakers = speakers;
        this.worldPercentage = worldPercentage;
    }

    public String getLanguage() {
        return language;
    }

    public long getSpeakers() {
        return speakers;
    }

    public double getWorldPercentage() {
        return worldPercentage;
    }
}
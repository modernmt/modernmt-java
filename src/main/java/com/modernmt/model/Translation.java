package com.modernmt.model;

public class Translation extends Model {

    private String translation;
    private String contextVector;
    private int characters;
    private int billedCharacters;
    private String detectedLanguage;
    private String[] altTranslations;
    private Boolean detectedProfanities;

    public String getTranslation() {
        return translation;
    }

    public String getContextVector() {
        return contextVector;
    }

    public int getCharacters() {
        return characters;
    }

    public int getBilledCharacters() {
        return billedCharacters;
    }

    public String getDetectedLanguage() {
        return detectedLanguage;
    }

    public String[] getAltTranslations() {
        return altTranslations;
    }

    public Boolean getDetectedProfanities() {
        return detectedProfanities;
    }
}

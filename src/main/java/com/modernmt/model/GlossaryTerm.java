package com.modernmt.model;

public class GlossaryTerm extends Model {

    private final String term;
    private final String language;

    public GlossaryTerm(String term, String language) {
        this.term = term;
        this.language = language;
    }

    public String getTerm() {
        return this.term;
    }

    public String getLanguage() {
        return this.language;
    }

}

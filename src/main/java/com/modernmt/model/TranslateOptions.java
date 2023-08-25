package com.modernmt.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TranslateOptions extends Model {

    private String priority;
    private String projectId;
    private Boolean multiline;
    private Integer timeout;
    private String format;
    private Integer altTranslations;
    private String session;
    private List<String> glossaries;
    private Boolean ignoreGlossaryCase;
    private Boolean maskProfanities;

    private String idempotencyKey;
    private Object metadata;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Boolean getMultiline() {
        return multiline;
    }

    public void setMultiline(Boolean multiline) {
        this.multiline = multiline;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getAltTranslations() {
        return altTranslations;
    }

    public void setAltTranslations(Integer altTranslations) {
        this.altTranslations = altTranslations;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public List<String> getGlossaries() {
        return glossaries;
    }

    public void setGlossaries(List<String> glossaries) {
        this.glossaries = glossaries;
    }

    public void setGlossaries(long[] glossaries) {
        this.glossaries = LongStream.of(glossaries).mapToObj(Long::toString).collect(Collectors.toList());
    }

    public Boolean getIgnoreGlossaryCase() {
        return ignoreGlossaryCase;
    }

    public void setIgnoreGlossaryCase(Boolean ignoreGlossaryCase) {
        this.ignoreGlossaryCase = ignoreGlossaryCase;
    }

    public Boolean getMaskProfanities() {
        return maskProfanities;
    }

    public void setMaskProfanities(Boolean maskProfanities) {
        this.maskProfanities = maskProfanities;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    @SuppressWarnings("unchecked")
    public <T> T getMetadata() {
        return (T) metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }
}

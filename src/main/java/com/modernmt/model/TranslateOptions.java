package com.modernmt.model;

public class TranslateOptions extends Model {

    private String priority;
    private String projectId;
    private Boolean multiline;
    private Integer timeout;
    private String format;
    private Integer altTranslations;

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
}

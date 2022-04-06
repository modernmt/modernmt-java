package com.modernmt.model;

public class ImportJob extends Model {

    private String id;
    private Long memory;
    private int size;
    private float progress;

    public String getId() {
        return id;
    }

    public Long getMemory() {
        return null;
    }

    public int getSize() {
        return size;
    }

    public float getProgress() {
        return progress;
    }

}
